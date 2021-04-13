package com.winter.factory;

import static org.junit.Assert.*;

import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;

import com.winter.autoconfig.testclasses.Car;
import com.winter.autoconfig.testclasses.Driver;

public class AnnotaionBasedBeanFactoryTest {
	
	private static AnnotaionBasedBeanFactory beanFactory;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		beanFactory= new AnnotaionBasedBeanFactory();
	}


	@Test
	public void testCreateBeansUnderPackage() {
		beanFactory.createBeansUnderPackage(Car.class.getPackageName());
		
	//	Assertions.assertThat(beanFactory.getBean(Car.class)).isNotNull();
	  	Driver driver = beanFactory.getBean(Driver.class);
	  	
		Assertions.assertThat(driver.getName()).isEqualTo("Yogesh");
	}

}
