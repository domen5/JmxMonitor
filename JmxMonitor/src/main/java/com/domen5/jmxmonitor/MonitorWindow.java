package com.domen5.jmxmonitor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Second; 
import org.jfree.data.time.TimeSeries; 
import org.jfree.data.time.DynamicTimeSeriesCollection; 
import org.jfree.data.xy.XYDataset; 

public class MonitorWindow extends JFrame{	

	private static final long serialVersionUID = 1L;
	static String host = "127.0.0.1";
	static int port = 9999;
	static final String OBJECT_NAME = "java.lang:type=Memory";
	static final String ATTRIBUTE_NAME = "HeapMemoryUsage";
	private static final int MAX_NUM = 50;
	private final JmxMonitor monitor;
	private final Timer timer;

	public MonitorWindow() {
		this.monitor = new JmxMonitorImpl();
		try {
			//		monitor.connect(host, port);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		this.setTitle("JMX Monitor");
		this.setSize(1000, 1000);
		this.setResizable(false);

		this.setLayout(new FlowLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		final DynamicTimeSeriesCollection dataset =
				new DynamicTimeSeriesCollection(1, MAX_NUM, new Second());


		dataset.setTimeBase(new Second(0, 0, 0, 1, 1, 2011));

		//		dataset.addSeries(data, 0, ATTRIBUTE_NAME);

		this.timer = new Timer(1000, new ActionListener() {
			float[] newData = new float[1];
			@Override
			public void actionPerformed(ActionEvent e) {
				newData[0] = getData();
				System.out.println(newData[0]);
				dataset.advanceTime();
				dataset.appendData(newData);
			}
		});

		final JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Heap Memory Usage", "", "", dataset, true, true, false );
//		final XYPlot     plot   = chart.getXYPlot();
//		ValueAxis        domain = plot.getDomainAxis();
//		domain.setAutoRange( true );
//		ValueAxis range = plot.getRangeAxis();
//		range.setRange( -11, 11);
		
		this.add(new ChartPanel(chart));
		this.setVisible(true);
		timer.start();
	}



	private float getData() {
		return (float) (new Random().nextFloat() * 10.0f);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(() -> new MonitorWindow());
	}

}
