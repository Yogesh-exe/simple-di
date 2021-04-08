package com.yogesh.simple_di.factorycreator;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.yogesh.simple_di.factorycreator.BeanFactoryInitializer;

public class BeanFactoryInitializerTest {

	BeanFactoryInitializer initializer= new BeanFactoryInitializer();
	@Test
	public void test() {
		
		initializer.initializeBeanFactory();
		
	}

}
