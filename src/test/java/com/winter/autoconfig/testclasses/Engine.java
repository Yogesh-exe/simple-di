package com.winter.autoconfig.testclasses;

import com.winter.factory.annotation.Component;

@Component
public class Engine {
	
	private String type;
	private Integer torque;
	public Engine() {
		super();
		System.out.println("Engine created");
	}

}
