package com.winter.factory.annotation.resolver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winter.autoconfig.helper.Pair;
import com.winter.factory.annotation.Autowired;
import com.winter.factory.annotation.Value;

public class ComponentResolver {

	private static final Logger logger = LoggerFactory.getLogger(ComponentResolver.class);

	public Set<Object> resolveComponent(Class<?> classToResolve) {
		Set<Object> assemblecBean = null;
		try {
			assemblecBean = new ComponentBuilder().assembleBean(classToResolve);
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
				| IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assemblecBean.forEach(System.out::println);
		return assemblecBean;
	}

	private class ComponentBuilder {

		public Set<Object> assembleBean(Class<?> beanToAssemble) throws InstantiationException, IllegalAccessException,
		NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
			logger.info("bean to assemble {}", beanToAssemble.getName());

			Set<Object> assembledBeans = new HashSet<>();

			if (Modifier.isStatic(beanToAssemble.getModifiers()) || beanToAssemble.isInterface())
				return Set.of();

			List<Object> assembledDependencies = assembleBeanDependencies(beanToAssemble);

			System.out.println("aall");
			assembledDependencies.forEach(System.out::println);

			assembledBeans.addAll(assembledDependencies);

			Constructor<?> constructor = getMainConstructor(beanToAssemble);
			Object newInstance = constructor.newInstance(assembledDependencies.toArray());
			assembledBeans.add(newInstance);
			return assembledBeans;
		}

		private List<Object> assembleBeanDependencies(Class<?> parentBean)
				throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException,
				IllegalArgumentException, InvocationTargetException {
			List<Parameter> parameters = getConstructorParameters(parentBean);

			Map<String, Object> assembledDependencies = parameters.stream()
					.filter(f -> Objects.nonNull(f.getAnnotation(Value.class)))
					.map(this::createInjectableField)
					.collect(Collectors.toMap(Pair::getKey, Pair::getValue));


			for (Parameter c : parameters) {
				if (!assembledDependencies.containsKey(c.getName())) {
					Object assembleBean = assembleBean(c.getType());
					assembledDependencies.put(c.getName(), assembleBean);
				}
			}
			return new ArrayList<>(assembledDependencies.values());
		}

		private List<Class<?>> classTypeOfdependencies(Class<?> parentBean) {
			return Optional.ofNullable(getConstructorParameters(parentBean)).orElse(Collections.emptyList()).stream()
					.map(Parameter::getType).collect(Collectors.toList());
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
						.filter(c -> Objects.nonNull(c.getAnnotation(Autowired.class)))
						.findFirst()
						.orElseThrow(()->new RuntimeException("Atleast one constructor should have @Autowired"));
			}
			return mainConstructor;
		}

		private Pair<String, Object> createInjectableField(Parameter field) {
			return new Pair<>(field.getName(), ValueResolver.getFieldValue(field));
		}

	}

}
