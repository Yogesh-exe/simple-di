package com.yogesh.simple_di.factory.configuration;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import com.yogesh.simple_di.factory.BeanFactory;
import com.yogesh.simple_di.factory.testclasses.Car;
import com.yogesh.simple_di.factory.testclasses.ConfigTestClass;

public class AnnotaionBasedConfigurationTest {

	AnnotaionBasedConfiguration annotaionBasedConfiguration = new AnnotaionBasedConfiguration();
	
	@Test
	public void test(){
		
		try {
			annotaionBasedConfiguration.resolveConfigClassDependency(ConfigTestClass.class);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
