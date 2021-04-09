package com.winter.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BruteBeanFactory implements BeanFactory{
	private static final Logger logger = LoggerFactory.getLogger(BruteBeanFactory.class);
	public Object getBean(final Class<?> beanClass) {
		Objects.requireNonNull(beanClass, "beanClass not provided");
		return BeanStore.getBean(beanClass);
	}

	public Object createBean(Class<?> bean) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		Objects.requireNonNull(bean, "bean not provided");
		if(BeanStore.containsBean(bean))
			return BeanStore.getBean(bean);
		
		Object assembleBean = BeanAssembly.assembleBean(bean);
		putBean(bean, assembleBean);
		return assembleBean;
	}

	public void putBean(Class<?> beanClass, Object bean) {
		BeanStore.addBean(beanClass, bean);
	}

	public BruteBeanFactory() {
		//throw new IllegalStateException("utility class");
	}


	private static class BeanAssembly {
		public static Object assembleBean(Class<?> beanToAssemble) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
			logger.debug("bean to assemble "+beanToAssemble.getName());

			if(Modifier.isStatic(beanToAssemble.getModifiers()) || beanToAssemble==BruteBeanFactory.class)
				return null;

			List<Object> assembledDependencies =assembleBeanDependencies(beanToAssemble);
			//			assembledDependencies.forEach(System.out::println);
			
			List<Class<?>> classes = classTypeOfdependencies(beanToAssemble);

			Constructor<?> constructor = beanToAssemble.getConstructor(classes.toArray(new Class[0]));
			if(Modifier.isPublic(constructor.getModifiers())) {
				Object newInstance = constructor.newInstance(assembledDependencies.toArray());
				BeanStore.addBean(beanToAssemble, newInstance);
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
