package com.winter.autoconfig.testclasses;

import com.winter.factory.annotation.Bean;
import com.winter.factory.annotation.Configuration;

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
