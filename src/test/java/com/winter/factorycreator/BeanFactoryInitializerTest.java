package com.winter.factorycreator;

import org.junit.Test;

import com.winter.factorycreator.BeanFactoryInitializer;

public class BeanFactoryInitializerTest {

	BeanFactoryInitializer initializer= new BeanFactoryInitializer();
	@Test
	public void test() {
		
		initializer.initializeBeanFactory();
		
	}

}