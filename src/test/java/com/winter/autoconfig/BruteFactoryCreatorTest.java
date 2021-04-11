package com.winter.autoconfig;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.winter.autoconfig.testclasses.Car;
import com.winter.factory.BeanFactory;

import static org.assertj.core.api.Assertions.*;

public class BruteFactoryCreatorTest {

	FactoryCreator factoryCreator= new BruteFactoryCreator();
	@Test
	public void testCreatedFactory() {

		BeanFactory createdFactory = factoryCreator.createFactory(getClass(),null);
		assertThat(createdFactory.getBean(Car.class)).isInstanceOf(Car.class);
	}

}
