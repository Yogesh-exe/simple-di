package com.yogesh.simple_di.property;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class PropertyFileReaderTest {

	PropertyFileReader reader = new PropertyFileReader();
	
	@Test
	public void test() {
		Assertions.assertThat(reader.getProperty("di.developer.name")).isEqualTo("Yogeshwar");
	}

}
