package com.domen5.jmxmonitor;

import java.io.IOException;

public class Main {
	static String host = "127.0.0.1";
	static int port = 9999;

	public static void main(String[] args) {
		try {
			JmxMonitor monitor = new JmxMonitorImpl();
			monitor.connect(host, port);
			
			System.out.println("\nMBeans count: " + monitor.getMBeanCount());
			
			System.out.println("\nDomains");
			var domains = monitor.getDomains();
			for(String domain : domains){
				System.out.println("Domain: " + domain);				
			}			
			
			System.out.println("\nObjectNames");
			var mbeanNames = monitor.getMBeanNames();
			for(String name : mbeanNames){
				System.out.println("ObjectName: " + name);				
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}

	}

}
