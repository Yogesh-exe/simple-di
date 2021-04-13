package com.winter.factory;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.winter.autoconfig.helper.ReflectionUtils;
import com.winter.factory.annotation.Component;
import com.winter.factory.annotation.Configuration;
import com.winter.factory.annotation.resolver.ComponentResolver;
import com.winter.factory.annotation.resolver.ConfigurationResolver;

public class AnnotaionBasedBeanFactory extends AbstractBeanFactory {

	private ComponentResolver componentResolver = new ComponentResolver();

	@Override
	public void createBeansUnderPackage(String basePackageName) {
		List<Class<?>> sources = ReflectionUtils.getClasses(basePackageName);

		Set<Object> configuredBean = sources.stream().filter(c -> Objects.nonNull(c.getAnnotation(Configuration.class)))
				.map(ConfigurationResolver::resolveConfigClassDependency).flatMap(List::stream)
				.collect(Collectors.toSet());

		putAll(configuredBean);

		Set<Object> componentBean = sources.stream().filter(c -> Objects.nonNull(c.getAnnotation(Component.class)))
				.filter(c->Objects.isNull(beanStore.getBean(c)))
				.map(componentResolver::resolveComponent).flatMap(Set::stream).collect(Collectors.toSet());

		putAll(componentBean);

	}

	@Override
	public Object createBean(Class<?> bean) {
		return componentResolver.resolveComponent(bean);
	}

}
