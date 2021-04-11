package com.winter.autoconfig;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winter.autoconfig.helper.ReflectionUtils;
import com.winter.factory.BeanFactory;
import com.winter.factory.BruteBeanFactory;

public class BruteFactoryCreator implements FactoryCreator{

	private BeanFactory beanFactory = new BruteBeanFactory();
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public BeanFactory createFactory(Class<?> mainClass, String[] args) {
		String basePackageName = mainClass.getPackage().getName();
		logger.debug(basePackageName);
		List<Class<?>> sources = ReflectionUtils.getClasses(basePackageName);

		sources.forEach(c -> beanFactory.createBean(c));
		return beanFactory;

	}
}
