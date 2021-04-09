package com.winter.factory.testclasses;

public class Car {
	
	public Driver driver;
	
	//public String name;

	public Car(Driver driver) {
		super();
		this.driver = driver;
		System.out.println("Car created");
	}

}
