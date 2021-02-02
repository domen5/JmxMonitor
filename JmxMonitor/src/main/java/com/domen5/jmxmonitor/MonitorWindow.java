package com.domen5.jmxmonitor;

import java.awt.GridLayout;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class MonitorWindow extends JFrame{	
	
	private static final long serialVersionUID = 1L;
	
	public MonitorWindow() {
		JPanel comp = new JPanel(new GridLayout(3, 3), false);		
	}
	
	public <T> void makeChart(String objectName, Collection<T> coll) {
		XYSeries series = new XYSeries(objectName);
		XYSeriesCollection dataset = new XYSeriesCollection(series);
		//timer -> invokeLater(() -> updateChart());
	}
	
	private void updateChart() {
		
	}
	
	

	public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new MonitorWindow());
	}

}
