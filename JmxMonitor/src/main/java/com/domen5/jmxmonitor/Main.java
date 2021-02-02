package com.domen5.jmxmonitor;

public class Main {
	static String host = "127.0.0.1";
	static int port = 9999;
	static final String objectName = "com.domen5.jmxserver:type=Counter";
	static final String attributeName = "Count";

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
			System.out.println("Count: " + monitor.getMBean(objectName, attributeName));


		}catch(Exception e) {
			System.out.println();
		}
	}
}
