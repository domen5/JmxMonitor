package com.domen5.jmxmonitor;

public class Main {
	private static final String WELCOME_MSG = "[1] Read an MBean from a remote JMXServer\n[2] Show a chart for a remote JMXServer Heap Memory Usage\n>";
	private static String host = "127.0.0.1";
	private static final String DEFAULT_HOST = "127.0.0.1";
	private static int port = 9999;
	private static final int DEFAULT_PORT = 9999;
	private static final String objectName = "com.domen5.jmxserver:type=Counter";
	private static final String attributeName = "Count";

	public static void main(String[] args) {
		// String objectName = "com.domen5.jmxserver:type=Counter";
		// String attributeName = "Count";

		try {
			final int opt = intInput(WELCOME_MSG);

			if (opt == 1) {
				String host = input("Insert host [127.0.01]: ");
				int port = intInput("Insert port[9999]: ");

				JmxMonitor monitor = new JmxMonitorImpl();
				monitor.connect(host, port);

				System.out.println("\nMBeans count: " + monitor.getMBeanCount());

				System.out.println("\nDomains");
				var domains = monitor.getDomains();
				for (String domain : domains) {
					System.out.println("Domain: " + domain);
				}

				System.out.println("\nObjectNames");
				var mbeanNames = monitor.getMBeanNames();
				for (String name : mbeanNames) {
					System.out.println("ObjectName: " + name);
				}

				String objectName = input("\nInsert MBean ObjectName [com.domen5.jmxserver:type=Counter]: ");
				monitor.getAttributesForObjectName(objectName).stream()
					.forEach(n -> System.out.println(n));
				String attributeName= input("\nInsert AttributeName [Count]: ");
				System.out.println(attributeName + ": " + monitor.getMBean(objectName, attributeName));
			} else if (opt == 2) {
				javax.swing.SwingUtilities.invokeLater(() -> new MonitorWindow());
			} else {
				System.out.println("Wrong input.");
			}
			System.exit(0);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}

	private static String input(String msg) {
		System.out.print(msg);
		String tmp = System.console().readLine();
		return tmp;
	}

	private static int intInput(String msg) {
		System.out.print(msg);
		String tmp = System.console().readLine();
		return Integer.parseInt(tmp);
	}
}
