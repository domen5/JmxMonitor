package com.domen5.jmxmonitor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.management.ObjectName;
import javax.management.ReflectionException;

public class JmxMonitorImpl implements JmxMonitor {
	private JMXServiceURL url;
	private MBeanServerConnection mbsc;
	private final String host;
	private final int port;

	public JmxMonitorImpl(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	public void connect() throws IOException {
		this.url = createConnectionURL(this.host, this.port);
		JMXConnector connector = JMXConnectorFactory.connect(url, null);
		this.mbsc = connector.getMBeanServerConnection();
	}

	@Override
	public List<String> getMBeanNames() throws IOException {
		return this.mbsc.queryNames(null, null).stream().map(o -> o.toString()).collect(Collectors.toList());
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
	public List<String> getAttributesForObjectName(String objectName) throws Exception {
		try {
			MBeanInfo info;
			info = this.mbsc.getMBeanInfo(new ObjectName(objectName));
			return Arrays.stream(info.getAttributes()).map(attr -> attr.getName()).collect(Collectors.toList());
		} catch (InstanceNotFoundException | IntrospectionException | MalformedObjectNameException | ReflectionException
				| IOException e) {
			e.printStackTrace();
			throw new Exception("Something went wrong while getting attributes.");
		}
	}

	@Override
	public Object getMBean(String objectName, String name) throws Exception {
		try {
			this.connect();
			return this.mbsc.getAttribute(new ObjectName(objectName), name);
		} catch (AttributeNotFoundException | InstanceNotFoundException | MalformedObjectNameException | MBeanException
				| ReflectionException | IOException e) {
			e.printStackTrace();
			throw new Exception("Something went wrong while getting the attribute");
		}
	}
}
