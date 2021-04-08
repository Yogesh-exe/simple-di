package com.yogesh.simple_di.factory.configuration;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;

import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;

import com.yogesh.simple_di.factory.BeanFactory;
import com.yogesh.simple_di.factory.BruteBeanFactory;
import com.yogesh.simple_di.factory.annotation.resolver.ConfigurationResolver;
import com.yogesh.simple_di.factory.testclasses.Car;
import com.yogesh.simple_di.factory.testclasses.ConfigTestClass;

public class ConfigurationResolverTest {

	private static ConfigurationResolver configurationResolver;
    private static Object bean;
    private static BeanFactory bruteBeanFactory;
    @BeforeClass
    public static void init() throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        bruteBeanFactory = new BruteBeanFactory();
        configurationResolver = new ConfigurationResolver();
    	bean = bruteBeanFactory.createBean(Car.class);
    }
	
	@Test
	public void test(){
		
		try {
			configurationResolver.resolveConfigClassDependency(ConfigTestClass.class,bruteBeanFactory);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			Assertions.assertThat(bruteBeanFactory.getBean(Car.class)).isNotNull();
		}

	}

}
