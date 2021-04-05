package com.yogesh.simple_di.factory;

import static org.junit.Assert.*;

import org.junit.Test;

public class BeanFactoryInitializerTest {

	BeanFactoryInitializer initializer= new BeanFactoryInitializer();
	@Test
	public void test() {
		
		initializer.initializeBeanFactory();
	}

}
