package com.winter.autoconfig.testclasses;

import com.winter.factory.annotation.Bean;
import com.winter.factory.annotation.Configuration;

@Configuration
public class ConfigTestClass {

//	@Bean
//	public Engine createEngine() {
//		return new Engine();
//	}

	public Car createCarNoAnnotaion() {
		return new Car();
	}

	@Bean
	public Driver createDriver() {
		return new Driver();
	}

}
