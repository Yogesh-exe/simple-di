package com.winter.autoconfig.testclasses;

import com.winter.factory.annotation.Value;

public class Driver {

	@Value("driver.name")
	private String name;

	public Driver() {
		System.out.println("Driver Created");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
