package com.domen5.jmxmonitor;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.awt.Dimension;

import javax.management.openmbean.CompositeData;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.data.time.Second;
import org.jfree.data.time.DynamicTimeSeriesCollection; 


public class MonitorWindow extends JFrame{	

	private static final long serialVersionUID = 1L;
	private static final String OBJECT_NAME = "java.lang:type=Memory";
	private static final String ATTRIBUTE_NAME = "HeapMemoryUsage";
	private static final String DEFAULT_HOST = "127.0.0.1";
	private static final int DEFAULT_PORT = 9999;
	private static final int MAX_NUM = 50;
	private String host;
	private int port;	
	
	private final JmxMonitor monitor;
	private Timer timer;

	public MonitorWindow() {
		this.monitor = new JmxMonitorImpl();
		this.setTitle("JMX Monitor");
		this.setResizable(false);
		JPanel mainPanel = new JPanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		mainPanel.setPreferredSize(new Dimension(720, 480));
		JTextField txtHost = new JTextField(DEFAULT_HOST);
		JTextField txtPort = new JTextField("" + DEFAULT_PORT);
		JButton btnStart = new JButton("Start");
		mainPanel.add(txtHost);
		mainPanel.add(txtPort);
		mainPanel.add(btnStart);
		this.add(mainPanel);
		this.pack();
		this.setVisible(true);
		JFrame frame = this;

		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				host = txtHost.getText();
				port = Integer.parseInt(txtPort.getText());

				try {
					monitor.connect(host, port);
				}catch(Exception e) {
					JOptionPane.showMessageDialog(frame, e.getMessage());
					frame.dispose();
				}

				DynamicTimeSeriesCollection dataset =
						new DynamicTimeSeriesCollection(1, MAX_NUM, new Second());

				dataset.addSeries(new float[0], 0, "Heap Memory Usage");
				dataset.setTimeBase(new Second(0, 0, 0, 1, 1, 2011));

				timer = new Timer(1000, new ActionListener() {
					float[] newData = new float[1];
					@Override
					public void actionPerformed(ActionEvent e) {
						newData[0] = getData();
						dataset.advanceTime();
						dataset.appendData(newData);
					}
				});

				JFreeChart chart = ChartFactory.createTimeSeriesChart(
						"Heap Memory Usage", "Time", "Bytes", dataset, true, true, false );
				NumberAxis rangeAxis = (NumberAxis) chart.getXYPlot().getRangeAxis();
				rangeAxis.setNumberFormatOverride(NumberFormat.getIntegerInstance());

				frame.remove(mainPanel);
				frame.add(new ChartPanel(chart));
				frame.pack();

				timer.start();
				
			}
		});


	}

	private long getData() {
		try {
			monitor.connect(host, port);
			CompositeData compData = (CompositeData) this.monitor.getMBean(OBJECT_NAME, ATTRIBUTE_NAME);
			System.out.println(compData);
			long data = (long) compData.get("used");
			System.out.println(data);
			return data;			
		}catch(Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}

		return  -1;
	}

	public static void main(String[] args) {
		new MonitorWindow();
	}

}
