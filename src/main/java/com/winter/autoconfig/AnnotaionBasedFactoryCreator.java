package com.winter.autoconfig;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winter.autoconfig.helper.ReflectionUtils;
import com.winter.factory.AnnotaionBasedBeanFactory;
import com.winter.factory.BeanFactory;
import com.winter.factory.annotation.Component;
import com.winter.factory.annotation.Configuration;
import com.winter.factory.annotation.resolver.ComponentResolver;
import com.winter.factory.annotation.resolver.ConfigurationResolver;

public class AnnotaionBasedFactoryCreator implements FactoryCreator {

	private BeanFactory beanFactory = new AnnotaionBasedBeanFactory();
	private final Logger logger = LoggerFactory.getLogger(getClass());


	public BeanFactory createFactory(Class<?> mainClass) {
		return createFactory(mainClass, null);
	}

	@Override
	public BeanFactory createFactory(Class<?> mainClass, String[] args) {
		String basePackageName = mainClass.getPackage().getName();
		logger.debug(basePackageName);

		List<Class<?>> sources = ReflectionUtils.getClasses(basePackageName);

		Set<Object> configuredBean = sources.stream()
		.filter(c-> Objects.nonNull(c.getAnnotation(Configuration.class)))
		.map(ConfigurationResolver::resolveConfigClassDependency)
		.flatMap(List::stream)
		.collect(Collectors.toSet());

		beanFactory.putAll(configuredBean);

		Set<Object> componentBean = sources.stream()
				.filter(c-> Objects.nonNull(c.getAnnotation(Component.class)))
				.map(ComponentResolver::resolveComponent)
				.collect(Collectors.toSet());

		beanFactory.putAll(componentBean);

		return beanFactory;

	}

}
