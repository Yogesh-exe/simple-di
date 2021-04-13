package com.winter.autoconfig;

import org.junit.Test;

import com.winter.autoconfig.testclasses.Car;
import com.winter.factory.BeanFactory;

import static org.assertj.core.api.Assertions.*;

public class BruteFactoryCreatorTest {

	FactoryCreator factoryCreator = new BruteFactoryCreator();

	@Test
	public void testCreatedFactory() {

		BeanFactory createdFactory = factoryCreator.createFactory(Car.class, null);
		Car car = createdFactory.getBean(Car.class);
		assertThat(car).isInstanceOf(Car.class);
	}

}
