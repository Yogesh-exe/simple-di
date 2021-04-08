package com.yogesh.simple_di.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BeanFactory {

	private static Map<Class<?>,Object> beanBag = new HashMap<>();


	public static Object getBean(final Class<?> beanClass) {
		if(beanClass == null) {
			throw new IllegalArgumentException("beanClass not provided");
		}
		return beanBag.get(beanClass);
	}

	public static Object createBean(Class<?> bean) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		if(bean == null) {
			throw new IllegalArgumentException("bean not provided");
		}
		Object assembleBean = BeanAssembly.assembleBean(bean);
		beanBag.put(bean, assembleBean);
		return assembleBean;
	}

	public static void putBean(Class<?> beanClass, Object bean) {
		beanBag.put(beanClass, bean);
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

			List<Object> assembledDependencies =assembleBeanDependencies(beanToAssemble);
			//			assembledDependencies.forEach(System.out::println);
			
			List<Class<?>> classes = classTypeOfdependencies(beanToAssemble);

			Constructor<?> constructor = beanToAssemble.getConstructor(classes.toArray(new Class[0]));
			if(Modifier.isPublic(constructor.getModifiers())) {
				Object newInstance = constructor.newInstance(assembledDependencies.toArray());
				beanBag.put(beanToAssemble, newInstance);
				return newInstance;
			}
			return null;

		}
		private static List<Object> assembleBeanDependencies(Class<?> parentBean) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{

			List<Class<?>> classes = classTypeOfdependencies(parentBean);

			// return classes.stream().map(c->assembleBean(c)).collect(Collectors.toList()); //unnecessary trouble of rethrowing ex
			
			List<Object> assembledDependencies = new ArrayList<>();
			
			for(Class<?> c : classes){
				Object assembleBean = assembleBean(c);
				assembledDependencies.add(assembleBean);
			}

			return assembledDependencies;
		}
		private static List<Class<?>> classTypeOfdependencies(Class<?> parentBean) {
			Field[] fields = parentBean.getFields();

			return Arrays.stream(fields)
			.filter(f->!"java.lang".equals(f.getType().getPackage().getName()))
			.map(Field::getType)
			.collect(Collectors.toList());
		}

		private BeanAssembly() {
			throw new IllegalStateException("utility class");
		}
	}


}
