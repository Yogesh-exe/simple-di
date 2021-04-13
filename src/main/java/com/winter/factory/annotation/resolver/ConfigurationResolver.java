package com.winter.factory.annotation.resolver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.winter.factory.annotation.Bean;
import com.winter.factory.exception.ExceptionWrapper;

public class ConfigurationResolver {

	public static List<Object> resolveConfigClassDependency(Class<?> configClass) {
		Object configClassInstance = null;
		List<Object> constructedBeans = new ArrayList<>();
		try {
			configClassInstance = configClass.getDeclaredConstructor().newInstance();
			Method[] methods = configClass.getMethods();

			for (Method m : methods) {
				if (m.isAnnotationPresent(Bean.class)) {
					Object createdBean = null;
					createdBean = m.invoke(configClassInstance, new Object[0]);
					constructedBeans.add(createdBean);
				}
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			ExceptionWrapper.wrappedException(e);
		}
		return constructedBeans;
	}
}
