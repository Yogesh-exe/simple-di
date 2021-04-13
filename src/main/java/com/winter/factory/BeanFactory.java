package com.winter.factory;

import java.util.Collection;
import java.util.List;

import com.winter.autoconfig.helper.ReflectionUtils;

public interface BeanFactory {

	public <T> T getBean(final Class<?> beanClass);

	public Object createBean(Class<?> bean);

	public default void createBeansUnderPackage(String basePackageName) {
		List<Class<?>> sources = ReflectionUtils.getClasses(basePackageName);
		sources.forEach(this::createBean);
	}
	
	public boolean hasBean(Class<?> bean);

	public void putBean(Class<?> beanClass, Object bean);

	default void putAll(Collection<Object> beanCollection) {
		beanCollection.forEach(c -> putBean(c.getClass(), c));
	}

}
