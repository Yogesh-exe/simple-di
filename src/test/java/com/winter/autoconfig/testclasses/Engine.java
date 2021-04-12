package com.winter.autoconfig.testclasses;

import com.winter.factory.annotation.Autowired;
import com.winter.factory.annotation.Component;

@Component
public class Engine {
	
	private String type;
	private Integer torque;
	private FuelInjector injector;
	
@Autowired
	public Engine(FuelInjector injector) {
		super();
		this.injector=injector;
		System.out.println("Engine created");
	}

}
