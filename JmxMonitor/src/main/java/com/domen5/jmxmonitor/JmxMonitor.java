package com.domen5.jmxmonitor;

import java.io.IOException;
import java.util.List;

public interface JmxMonitor {
	public void connect(String host, int port) throws IOException;
	public List<String> getMBeanNames() throws IOException;
	public List<String> getDomains() throws IOException;
	public int getMBeanCount() throws IOException;
	public String getMBean(String name);

}
