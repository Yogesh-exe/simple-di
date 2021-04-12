package com.winter.autoconfig.testclasses;

import com.winter.factory.annotation.Autowired;
import com.winter.factory.annotation.Value;

public class Driver {

	@Value("driver.name")
	private String name;

	public Driver() {
		System.out.println("Driver Created");
	}
	
	

	@Autowired
	public Driver(@Value("driver.name") String name) {
		System.out.println("Driver Created"+name);
		this.name = name;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
