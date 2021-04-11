package com.winter.factory.annotation.resolver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.winter.factory.annotation.Bean;

public class ConfigurationResolver {
	
		
	public static List<Object> resolveConfigClassDependency(Class<?> configClass){
		Object configClassInstance = null;
		try {
			configClassInstance = configClass.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Method[] methods = configClass.getMethods();
		List<Object> constructedBeans = new ArrayList<>();
		
		for(Method m: methods) {
			if(m.isAnnotationPresent(Bean.class)) {
				System.out.println("methode"+m.getName());
				Class<?> classToCreate = m.getReturnType();
				/*Class<?>[] parameterTypes = m.getParameterTypes();
				Object[] methodparametersObjects;
				Arrays.stream(parameterTypes)
				.map(parameter -> beanFactory.getBean(parameter))
				.collect(Collectors.toList());*/
				Object createdBean = null;
				try {
					createdBean = m.invoke(configClassInstance, new Object[0]);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				constructedBeans.add(createdBean);
				//beanFactory.putBean(classToCreate,classToCreate.cast(createdBean));
			}
		}
		return constructedBeans;
	}
}
