package com.winter.factory.configuration;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;

import com.winter.autoconfig.testclasses.Car;
import com.winter.autoconfig.testclasses.ConfigTestClass;
import com.winter.autoconfig.testclasses.Driver;
import com.winter.factory.BeanFactory;
import com.winter.factory.BruteBeanFactory;
import com.winter.factory.annotation.resolver.ConfigurationResolver;

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

			List<Object> resolveConfigClassDependency = configurationResolver.resolveConfigClassDependency(ConfigTestClass.class);
			Assertions.assertThat(resolveConfigClassDependency).anyMatch(c->c.getClass()==Driver.class);
		}
}
