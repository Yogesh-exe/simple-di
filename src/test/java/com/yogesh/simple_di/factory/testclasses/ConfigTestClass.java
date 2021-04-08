package com.yogesh.simple_di.factory.testclasses;

import com.yogesh.simple_di.factory.annotation.Bean;
import com.yogesh.simple_di.factory.annotation.Configuration;

@Configuration
public class ConfigTestClass {
	
	@Bean
	public Car createCar() {
		return new Car(null);
	}
	
	public Car createCarNoAnnotaion() {
		return new Car(null);
	}
	
	@Bean
	public Driver createDriver() {
		return new Driver();
	}

}
