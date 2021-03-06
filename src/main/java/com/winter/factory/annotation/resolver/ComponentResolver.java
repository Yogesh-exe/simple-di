package com.winter.factory.annotation.resolver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winter.autoconfig.helper.Pair;
import com.winter.factory.BeanFactory;
import com.winter.factory.annotation.Autowired;
import com.winter.factory.annotation.Value;
import com.winter.factory.exception.ExceptionWrapper;

public class ComponentResolver {

	private static final Logger logger = LoggerFactory.getLogger(ComponentResolver.class);

	public Set<Object> resolveComponent(Class<?> classToResolve, BeanFactory beanFactory) {
		Set<Object> assembledBean = new HashSet<>();
		try {
			assembledBean = new ComponentBuilder().assembleBean(classToResolve, assembledBean,beanFactory);
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
				| IllegalArgumentException | InvocationTargetException e) {
			ExceptionWrapper.wrappedException(e);
		}

		return assembledBean;
	}

	private class ComponentBuilder {

		public Set<Object> assembleBean(Class<?> beanToAssemble, Set<Object> assembledBeans, BeanFactory beanFactory)
				throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException,
				IllegalArgumentException, InvocationTargetException {
			logger.info("bean to assemble {}", beanToAssemble.getName());

			if (Modifier.isStatic(beanToAssemble.getModifiers()) || beanToAssemble.isInterface())
				return Set.of();
			if(beanFactory.hasBean(beanToAssemble))
				return Set.of((Object)beanFactory.getBean(beanToAssemble));

			List<Object> assembledDependencies = new ArrayList<>();

			List<Parameter> parameters = getConstructorParameters(beanToAssemble);

			Map<String, Object> assembledDependenciesmap = parameters.stream()
					.filter(f -> Objects.nonNull(f.getAnnotation(Value.class))).map(this::createInjectableField)
					.collect(Collectors.toMap(Pair::getKey, Pair::getValue));

			assembledDependencies.addAll(assembledDependenciesmap.values());

			for (Parameter c : parameters) {
				if (!assembledDependenciesmap.containsKey(c.getName())) {
					Set<Object> beans = assembleBean(c.getType(), assembledBeans,beanFactory);
					beans.stream().filter(b -> b.getClass() == c.getType()).collect(Collectors.toList());
					assembledDependencies.addAll(
							beans.stream().filter(b -> b.getClass() == c.getType()).collect(Collectors.toList()));
				}
			}

			Constructor<?> constructor = getMainConstructor(beanToAssemble);
			Object newInstance = constructor.newInstance(assembledDependencies.toArray());
			assembledBeans.add(newInstance);
			return assembledBeans;
		}

		private List<Parameter> getConstructorParameters(Class<?> parentBean) {
			Constructor<?> mainConstructor = getMainConstructor(parentBean);

			return Arrays.asList(mainConstructor.getParameters());

		}

		private Constructor<?> getMainConstructor(Class<?> parentBean) {
			Constructor<?>[] constructors = parentBean.getConstructors();
			Constructor<?> mainConstructor;
			if (constructors.length == 0)
				throw new RuntimeException("No public constructor");
			else if (constructors.length == 1) {
				mainConstructor = constructors[0];
			} else {
				mainConstructor = Arrays.stream(constructors)
						.filter(c -> Objects.nonNull(c.getAnnotation(Autowired.class))).findFirst()
						.orElseThrow(() -> new RuntimeException("At least one constructor should have @Autowired"));
			}
			return mainConstructor;
		}

		private Pair<String, Object> createInjectableField(Parameter field) {
			return new Pair<>(field.getName(), ValueResolver.getFieldValue(field));
		}

	}

}
