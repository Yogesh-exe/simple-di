package com.yogesh.simple_di.factory.configuration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.yogesh.simple_di.factory.BeanFactory;
import com.yogesh.simple_di.factory.annotation.Bean;

public class AnnotaionBasedConfiguration {
	
		
	public void resolveConfigClassDependency(Class<?> configClass) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Object configClassInstance = configClass.getDeclaredConstructor().newInstance();
		Method[] methods = configClass.getMethods();
		
		for(Method m: methods) {
			if(m.isAnnotationPresent(Bean.class)) {
				Class<?> classToCreate = m.getReturnType();
				Object createdBean = m.invoke(configClassInstance, new Object[0]);
				BeanFactory.putBean(classToCreate,classToCreate.cast(createdBean));
			}
		}
	}
}
