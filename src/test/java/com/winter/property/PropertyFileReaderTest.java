package com.winter.property;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class PropertyFileReaderTest {

	@Test
	public void testgetProperty_StringValue() {
		String name = PropertyFileReader.getProperty("di.developer.name",String.class);
		Assertions.assertThat(name).isEqualTo("Yogeshwar");
	}
	
	@Test
	public void testgetProperty_NumberValue() {
		Integer property = PropertyFileReader.getProperty("di.developer.age",Integer.class);
		Assertions.assertThat(property).isEqualTo(26);
		
		Double d = PropertyFileReader.getProperty("di.developer.age",Double.class);
		Assertions.assertThat(d).isEqualTo(26);
	}
	
	@Test
	public void testgetProperty_BooleanValue() {
		Object property = PropertyFileReader.getProperty("di.developer.exist",Boolean.class);
		Assertions.assertThat(property).isEqualTo(true);
	}

}
