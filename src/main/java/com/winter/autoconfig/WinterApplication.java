package com.winter.autoconfig;

import com.winter.factory.BeanFactory;

public class WinterApplication {

	private static FactoryCreator factoryCreator = new BruteFactoryCreator();

	public static BeanFactory run(Class<?> mainClass) {
		return run(mainClass, new String[0]);
	}

	public static BeanFactory run(Class<?> mainClass, String... args) {
		return factoryCreator.createFactory(mainClass, args);
	}

}
