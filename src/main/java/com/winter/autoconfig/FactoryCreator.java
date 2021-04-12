package com.winter.autoconfig;

import com.winter.factory.BeanFactory;

public interface FactoryCreator {

	public BeanFactory createFactory(Class<?> mainClass, String[] args);

}
