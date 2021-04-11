package com.winter.autoconfig.testclasses;

public class Car {

	private Driver driver;

	//public String name;

	public Car() {
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
