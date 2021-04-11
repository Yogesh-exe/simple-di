package com.winter.factory.annotation.resolver;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winter.factory.BeanStore;
import com.winter.factory.BruteBeanFactory;
import com.winter.factory.annotation.Value;

public class ComponentResolver {

	private static final Logger logger = LoggerFactory.getLogger(ComponentResolver.class);

	private ValueResolver valueResolver;

	public ComponentResolver(ValueResolver valueResolver) {
		super();
		this.valueResolver = valueResolver;
	}

	public static Object resolveComponent(Class<?> classToResolve) {

		Object assemblecBean = null;
		try {
			assemblecBean = BeanAssembly.assembleBean(classToResolve);
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
				| IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return assemblecBean;
	}

	private static class BeanAssembly {
		private static ValueResolver valueResolver = new ValueResolver();
		public static Object assembleBean(Class<?> beanToAssemble) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
			logger.info("bean to assemble {}",beanToAssemble.getName());

			if(Modifier.isStatic(beanToAssemble.getModifiers())
				|| beanToAssemble==BruteBeanFactory.class
				|| beanToAssemble.isInterface())
				return null;

			List<Object> assembledDependencies =assembleBeanDependencies(beanToAssemble);

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
			Field[] fields = parentBean.getFields();
			List<Object> assembledDependencies = Arrays.stream(fields).filter(f -> null != f.getAnnotation(Value.class)).map(BeanAssembly::createInjectableField).collect(Collectors.toList());


			for(Class<?> c : classes){
				Object assembleBean = assembleBean(c);
				assembledDependencies.add(assembleBean);
			}
			return assembledDependencies;
		}
		private static List<Class<?>> classTypeOfdependencies(Class<?> parentBean) {
			Field[] fields = parentBean.getFields();

			return Arrays.stream(fields)
			.map(Field::getType)
			.collect(Collectors.toList());
		}

		private static Object createInjectableField(Field field) {
			return valueResolver.getFieldValue(field);
		}

		private BeanAssembly() {
			throw new IllegalStateException("utility class");
		}
	}


}
