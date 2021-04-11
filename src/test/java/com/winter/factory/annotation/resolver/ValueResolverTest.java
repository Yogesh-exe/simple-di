package com.winter.factory.annotation.resolver;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.winter.autoconfig.testclasses.Driver;

public class ValueResolverTest {

	ValueResolver valueResolver = new ValueResolver();
	
	@Test
	public void testGetFieldValue() {
		try {
			Field field = Driver.class.getField("name");
			Assertions.assertThat(valueResolver.getFieldValue(field)).isEqualTo("Yogesh");
			
		} catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
