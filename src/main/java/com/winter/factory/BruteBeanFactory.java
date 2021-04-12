package com.winter.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winter.factory.annotation.Autowired;
import com.winter.factory.exception.ExceptionWrapper;

public class BruteBeanFactory extends AbstractBeanFactory{
		
	private static final Logger logger = LoggerFactory.getLogger(BruteBeanFactory.class);
	
	private BeanAssembly beanAssembly = new BeanAssembly();
	
	@Override
	public Object createBean(Class<?> bean) {
		Objects.requireNonNull(bean, "bean not provided");
		if(beanStore.containsBean(bean))
			return beanStore.getBean(bean);
		
		Object assembleBean = null;
		try {
			assembleBean = beanAssembly.assembleBean(bean);
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
				| IllegalArgumentException | InvocationTargetException e) {
			throw ExceptionWrapper.wrappedException(e);
		}
		putBean(bean, assembleBean);
		return assembleBean;
	}

	public BruteBeanFactory() {
		//throw new IllegalStateException("utility class");
	}


	private class BeanAssembly {
		
		public Object assembleBean(Class<?> beanToAssemble) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
			logger.info("bean to assemble "+beanToAssemble.getName());

			if(Modifier.isStatic(beanToAssemble.getModifiers()) 
				|| beanToAssemble==BruteBeanFactory.class
				|| beanToAssemble.isInterface())
				return null;

			List<Object> assembledDependencies =assembleBeanDependencies(beanToAssemble);
			
			List<Class<?>> classes = classTypeOfdependencies(beanToAssemble);

			Constructor<?> constructor = beanToAssemble.getConstructor(classes.toArray(new Class[0]));
			if(Modifier.isPublic(constructor.getModifiers())) {
				Object newInstance = constructor.newInstance(assembledDependencies.toArray());
				beanStore.addBean(beanToAssemble, newInstance);
				return newInstance;
			}
			return null;

		}
		private List<Object> assembleBeanDependencies(Class<?> parentBean) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
			List<Class<?>> classes = classTypeOfdependencies(parentBean);
			// return classes.stream().map(c->assembleBean(c)).collect(Collectors.toList()); //unnecessary trouble of rethrowing ex	
			List<Object> assembledDependencies = new ArrayList<>();
			
			for(Class<?> c : classes){
				Object assembleBean = assembleBean(c);
				assembledDependencies.add(assembleBean);
			}
			return assembledDependencies;
		}
		private List<Class<?>> classTypeOfdependencies(Class<?> parentBean) {
			Constructor<?>[] constructors = parentBean.getConstructors();
			
			Parameter[] parameters = Arrays.stream(constructors)
			.findFirst()
			.orElseThrow(()->new RuntimeException("No Constructor")).getParameters();


			return Arrays.stream(parameters)
			.filter(f->!"java.lang".equals(f.getType().getPackage().getName()))
			.map(Parameter::getType)
			.collect(Collectors.toList());
		}

	}


}
