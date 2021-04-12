package com.winter.autoconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winter.factory.BeanFactory;
import com.winter.factory.BruteBeanFactory;

public class BruteFactoryCreator implements FactoryCreator {

	private BeanFactory beanFactory;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public BeanFactory createFactory(Class<?> mainClass, String[] args) {
		beanFactory = new BruteBeanFactory();
		String basePackageName = mainClass.getPackage().getName();
		logger.debug(basePackageName);
		beanFactory.createBeansUnderPackage(basePackageName);
		return beanFactory;

	}
}
