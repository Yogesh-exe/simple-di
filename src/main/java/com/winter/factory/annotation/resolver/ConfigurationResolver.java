package com.winter.factory.annotation.resolver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.winter.factory.BeanFactory;
import com.winter.factory.annotation.Bean;

public class ConfigurationResolver {
	
		
	public void resolveConfigClassDependency(Class<?> configClass, BeanFactory beanFactory) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Object configClassInstance = configClass.getDeclaredConstructor().newInstance();
		Method[] methods = configClass.getMethods();
		
		for(Method m: methods) {
			if(m.isAnnotationPresent(Bean.class)) {
				Class<?> classToCreate = m.getReturnType();
				/*Class<?>[] parameterTypes = m.getParameterTypes();
				Object[] methodparametersObjects;
				Arrays.stream(parameterTypes)
				.map(parameter -> beanFactory.getBean(parameter))
				.collect(Collectors.toList());*/
				Object createdBean = m.invoke(configClassInstance, new Object());
				beanFactory.putBean(classToCreate,classToCreate.cast(createdBean));
			}
		}
	}
}
