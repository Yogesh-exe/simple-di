package com.winter.autoconfig.testclasses;

import com.winter.factory.annotation.Autowired;
import com.winter.factory.annotation.Component;

@Component
public class Car {

	private Driver driver;
	
	private Engine engine;

	//public String name;

	public Car() {
	}

	@Autowired
	public Car(Driver driver,Engine engine) {
		super();
		this.driver = driver;
		this.engine=engine;
		System.out.println("Car created");
	}

	public Car(Driver driver) {
		super();
		this.driver = driver;
		System.out.println("Car created");
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}
}
