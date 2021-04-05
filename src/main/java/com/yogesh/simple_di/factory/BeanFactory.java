package com.yogesh.simple_di.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanFactory {

	private static Map<Class<?>,Object> beanBag = new HashMap<>();


	public static Object getBean(final Class<?> beanClass) {
		if(beanClass == null) {
			throw new IllegalArgumentException("beanClass not provided");
		}
		return beanBag.get(beanClass);
	}

	public static Object addBean(Class<?> bean) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		if(bean == null) {
			throw new IllegalArgumentException("bean not provided");
		}
		Object assembleBean = BeanAssembly.assembleBean(bean);
		beanBag.put(bean, assembleBean);
		return assembleBean;
	}

	private BeanFactory() {
		throw new IllegalStateException("utility class");
	}


	private static class BeanAssembly {
		public static Object assembleBean(Class<?> beanToAssemble) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
			System.out.println("bean to assemble "+beanToAssemble.getName());

			if(Modifier.isStatic(beanToAssemble.getModifiers()) || beanToAssemble==BeanFactory.class)
				return null;

			if(beanBag.containsKey(beanToAssemble))
				return beanBag.get(beanToAssemble);

			Field[] fields = beanToAssemble.getFields();
			List<Class<?>> classes = new ArrayList<>();

			for(Field f: fields) {
				if(!"java.lang".equals(f.getType().getPackage().getName()))
					classes.add(f.getType());
			}

			List<Object> assembledDependencies = new ArrayList<>();
			for(Class<?> c : classes){
				Object assembleBean = assembleBean(c);
				assembledDependencies.add(assembleBean);
			}

//			assembledDependencies.forEach(System.out::println);

			Constructor<?> constructor = beanToAssemble.getConstructor(classes.toArray(new Class[0]));
			if(Modifier.isPublic(constructor.getModifiers())) {
				Object newInstance = constructor.newInstance(assembledDependencies.toArray());
				beanBag.put(beanToAssemble, newInstance);
				return newInstance;
			}
			return null;

		}

		private BeanAssembly() {
			throw new IllegalStateException("utility class");
		}
	}


}
