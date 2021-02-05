package com.domen5.jmxmonitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.management.openmbean.CompositeData;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

public class MonitorWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final String OBJECT_NAME = "java.lang:type=Memory";
	private static final String ATTRIBUTE_NAME = "HeapMemoryUsage";
	private static final String DEFAULT_HOST = "127.0.0.1";
	private static final int DEFAULT_PORT = 9999;
	private static final int MAX_NUM = 50;

	private String host;
	private int port;
	private int threshold;

	private JmxMonitor monitor;
	private Timer timer;
	private boolean noticeFlag;

	public MonitorWindow() {
		this.setTitle("JMX Monitor");
		this.setResizable(false);
		JPanel mainPanel = new JPanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainPanel.setPreferredSize(new Dimension(400, 400));
		mainPanel.setLayout(new GridLayout(4, 2));
		JTextField txtHost = new JTextField(DEFAULT_HOST);
		JTextField txtPort = new JTextField(DEFAULT_PORT + "");
		JTextField txtThreshold = new JTextField(1000 + "");
		JLabel lblHost = new JLabel("Host: ");
		JLabel lblPort = new JLabel("Port: ");
		JLabel lblThreshold = new JLabel("Treshold [MB]: ");
		JButton btnStart = new JButton("Start");
		mainPanel.add(lblHost);
		mainPanel.add(txtHost);
		mainPanel.add(lblPort);
		mainPanel.add(txtPort);
		mainPanel.add(lblThreshold);
		mainPanel.add(txtThreshold);
		mainPanel.add(btnStart);
		this.add(mainPanel);
		this.pack();
		this.setVisible(true);

		JFrame frame = this;
		this.noticeFlag = false;

		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				host = txtHost.getText();
				port = Integer.parseInt(txtPort.getText());
				threshold = Integer.parseInt(txtThreshold.getText()) * 1024 * 1024;

				try {
					monitor = new JmxMonitorImpl(host, port);
					monitor.connect();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(frame, e.getMessage());
					frame.dispose();
					System.exit(1);
				}

				DynamicTimeSeriesCollection dataset = new DynamicTimeSeriesCollection(1, MAX_NUM, new Second());

				dataset.addSeries(new float[0], 0, "Heap Memory Usage");
				dataset.setTimeBase(new Second(0, 0, 0, 1, 1, 2011));

				timer = new Timer(1000, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						long lData = getData();

						// it seems that JFreechart accepts only float values
						float[] newData = { (float) lData };

						dataset.advanceTime();
						dataset.appendData(newData);
						if (!noticeFlag && lData > threshold) {
							JOptionPane.showMessageDialog(frame,
									ATTRIBUTE_NAME + ": " + lData + " Bytes excedes threshold value: " + threshold
									+ " Bytes \nThis will not be shown again.");
							noticeFlag = true;
						}
					}
				});

				JFreeChart chart = ChartFactory.createTimeSeriesChart("Heap Memory Usage", "Time", "Bytes", dataset,
						true, true, false);
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
			CompositeData compData = (CompositeData) this.monitor.getMBean(OBJECT_NAME, ATTRIBUTE_NAME);
			long data = (long) compData.get("used");
			return data;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		return -1;
	}

	public static void main(String[] args) {
		new MonitorWindow();
	}

}
