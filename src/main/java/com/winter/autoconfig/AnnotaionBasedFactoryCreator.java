package com.winter.autoconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winter.factory.AnnotaionBasedBeanFactory;
import com.winter.factory.BeanFactory;

public class AnnotaionBasedFactoryCreator implements FactoryCreator {

	private BeanFactory beanFactory;
	private final Logger logger = LoggerFactory.getLogger(getClass());


	public BeanFactory createFactory(Class<?> mainClass) {
		return createFactory(mainClass, null);
	}

	@Override
	public BeanFactory createFactory(Class<?> mainClass, String[] args) {
		beanFactory = new AnnotaionBasedBeanFactory();
		String basePackageName = mainClass.getPackage().getName();
		logger.debug(basePackageName);
		beanFactory.createBeansUnderPackage(basePackageName);

		return beanFactory;
	}

}
