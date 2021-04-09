package com.winter.property;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.winter.property.PropertyFileReader;

public class PropertyFileReaderTest {

	PropertyFileReader reader = new PropertyFileReader();
	
	@Test
	public void test() {
		Assertions.assertThat(reader.getProperty("di.developer.name")).isEqualTo("Yogeshwar");
	}

}
