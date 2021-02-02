package com.domen5.jmxmonitor;

import java.io.IOException;
import java.util.List;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;

public interface JmxMonitor {
	public void connect(String host, int port) throws IOException;
	public List<String> getMBeanNames() throws IOException;
	public List<String> getDomains() throws IOException;
	public int getMBeanCount() throws IOException;
	public Object getMBean(String objectName, String name)
	throws AttributeNotFoundException, InstanceNotFoundException,
			MalformedObjectNameException, MBeanException, ReflectionException, IOException;	
}
