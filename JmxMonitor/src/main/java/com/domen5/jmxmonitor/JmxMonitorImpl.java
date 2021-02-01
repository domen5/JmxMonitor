package com.domen5.jmxmonitor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JmxMonitorImpl implements JmxMonitor {
	private JMXServiceURL url;
	private MBeanServerConnection mbsc;

	@Override
	public void connect(String host, int port) throws IOException {
		this.url = createConnectionURL(host, port);
		JMXConnector connector = JMXConnectorFactory.connect(url, null);
		this.mbsc = connector.getMBeanServerConnection();
	}

	@Override
	public List<String> getMBeanNames() throws IOException {
		return  this.mbsc.queryNames(null, null)
				.stream()
				.map(o ->o.toString())
				.collect(Collectors.toList());
	}

	private static JMXServiceURL createConnectionURL(String host, int port) throws MalformedURLException {
		return new JMXServiceURL("rmi", "", 0, "/jndi/rmi://" + host + ":" + port + "/jmxrmi");
	}

	@Override
	public List<String> getDomains() throws IOException {
		return Arrays.asList(mbsc.getDomains());
	}
	
	@Override
	public int getMBeanCount() throws IOException {
		return this.mbsc.getMBeanCount();
	}

	@Override
	public String getMBean(String name) {
		return name;
	}


}
