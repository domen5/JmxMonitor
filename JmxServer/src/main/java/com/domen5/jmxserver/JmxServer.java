package com.domen5.jmxserver;

import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import javax.management.ObjectName;


public class JmxServer {
	private static final String URL = "com.domen5.jmxserver:type=Counter";
	private Counter mbeanCounter;

	public JmxServer() throws Exception {
		
		MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();

		ObjectName mbeanName = new ObjectName(URL);
		this.mbeanCounter = new Counter();
		mbeanServer.registerMBean(this.mbeanCounter, mbeanName);
	}
	
	public void addNumber(int n) {
		this.mbeanCounter.addNumber(n);
	}

}
