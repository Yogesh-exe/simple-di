package com.yogesh.simple_di.factory;

import java.lang.reflect.InvocationTargetException;

public interface BeanFactory {
	
	public Object getBean(final Class<?> beanClass);
	
	public Object createBean(Class<?> bean) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException;

	public void putBean(Class<?> beanClass, Object bean);

}
