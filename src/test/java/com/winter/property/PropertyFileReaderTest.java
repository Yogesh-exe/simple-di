package com.winter.property;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class PropertyFileReaderTest {

	@Test
	public void testgetProperty() {
		Assertions.assertThat(PropertyFileReader.getProperty("di.developer.name")).isEqualTo("Yogeshwar");
	}

}
