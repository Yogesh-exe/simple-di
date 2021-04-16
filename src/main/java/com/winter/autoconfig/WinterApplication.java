package com.winter.autoconfig;

import com.winter.factory.BeanFactory;
import com.winter.factory.annotation.EnableAutoConfiguration;

public class WinterApplication {

	private static FactoryCreator factoryCreator;

	public static BeanFactory run(Class<?> mainClass) {
		return run(mainClass, new String[0]);
	}

	public static BeanFactory run(Class<?> mainClass, String... args) {
		if(mainClass.getAnnotation(EnableAutoConfiguration.class) != null) {
			factoryCreator = new AnnotaionBasedFactoryCreator();
			return factoryCreator.createFactory(mainClass, args);
		}
		else {
			factoryCreator = new BruteFactoryCreator();
			return factoryCreator.createFactory(mainClass, args);
		}
	}

}
