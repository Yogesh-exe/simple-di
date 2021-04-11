package com.winter.property;

import java.io.InputStream;
import java.util.Properties;

public class PropertyFileReader {

	private Properties properties;


	private static final String DEFAULT_PROPERTY_FILE ="application.properties";

	private void readPropertyFile(String propertyFile ) {
		System.out.println(propertyFile);
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try (InputStream resourceStream = loader.getResourceAsStream(propertyFile)) {
			properties.load(resourceStream);
		} catch (Exception e) {
		    e.printStackTrace();
		}

	}

	public Object getProperty(String key) {

		return properties.get(key);
	}

	public PropertyFileReader(String propertyFileName) {
		properties= new Properties();
		readPropertyFile(propertyFileName);
	}

	public PropertyFileReader() {
		this(DEFAULT_PROPERTY_FILE);
	}


}
