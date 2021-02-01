package com.domen5.jmxserver;

public class Counter implements CounterMBean{
	private int count;
	
	public Counter() {
		this.count = 0;
	}
	
	public void addNumber(int n) {
		this.count += n;
	}
	
	public int getCount() {
		return this.count;
	}

}
