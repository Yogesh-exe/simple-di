package com.winter.autoconfig;

import com.winter.factory.BeanFactory;

public class WinterApplication {
	
	private FactoryCreator factoryCreator;
	
	public BeanFactory run(Class<?>mainClass, String... args) {
		return factoryCreator.createFactory(mainClass,args);
	}

}
