package com.winter.factory.annotation.resolver;

import com.winter.autoconfig.testclasses.Car;
import com.winter.factory.AnnotaionBasedBeanFactory;
import com.winter.factory.BeanFactory;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Set;

public class ComponentResolverTest {

	private static ComponentResolver componentResolver;
	
	private static BeanFactory beanFactory;

	@BeforeClass
	public static void setUp() {
		componentResolver = new ComponentResolver();
		beanFactory = new AnnotaionBasedBeanFactory();
	}

	@Test
	public void resolveComponent() {
		Set<Object> resolveComponent = componentResolver.resolveComponent(Car.class, beanFactory);
		Assertions.assertThat(resolveComponent).hasAtLeastOneElementOfType(Car.class);
//        Assertions.assertThat(resolveComponent).hasFieldOrProperty("driver");
//        Assertions.assertThat(car.getDriver().getName()).isEqualTo("Yogesh");
	}
}
