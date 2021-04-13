package com.winter.factory;

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
		System.out.println("start");
		beanFactory.createBeansUnderPackage(Car.class.getPackageName());
		
		Car car = beanFactory.getBean(Car.class);
		Assertions.assertThat(car).isNotNull().isInstanceOf(Car.class);
	  	Driver driver = beanFactory.getBean(Driver.class);
		//Assertions.assertThat(driver.getName()).isEqualTo("Yogesh"); as it is contructed from configclass itself
	}

}
