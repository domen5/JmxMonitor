package com.domen5.jmxserver;

public class Main {

	public static void main(String[] args) throws Exception {
		JmxServer server = new JmxServer();		
		
		System.out.println("Server up and wainting.");
		Thread.sleep(5000);
		for(int i = 0; i < 10; i++) {
			Thread.sleep(1000);
			server.addNumber(i);
		}
		Thread.sleep(Long.MAX_VALUE);
	}
}
