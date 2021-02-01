package com.domen5.jmxserver;

import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import javax.management.ObjectName;

public class Main {

	public static void main(String[] args) throws Exception {
		JmxServer server = new JmxServer();
		
		Thread.sleep(5000);
		System.out.println("Server up and wainting.");
		
		for(int i = 0; i < 10; i++) {
			server.addNumber(i);
			Thread.sleep(5000);
		}
		Thread.sleep(Long.MAX_VALUE);
	}
}
