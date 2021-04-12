package com.winter.property;

import java.io.InputStream;
import java.util.Properties;

public class PropertyFileReader {

	private static Properties properties = new Properties();

	private static void readPropertyFile() {
		String profile = null != System.getProperty("winter.profile.active")?System.getProperty("winter.profile.active"):"";
		String propertyFile = new StringBuilder()
								.append("application")
								.append(profile)
								.append(".properties").toString();
		System.out.println(propertyFile);
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try (InputStream resourceStream = loader.getResourceAsStream(propertyFile)) {
			properties.load(resourceStream);
		} catch (Exception e) {
		    e.printStackTrace();
		}

	}
	static {
		readPropertyFile();
	}

	public static Object getProperty(String key) {
		return properties.get(key);
	}
	
	private PropertyFileReader() {}

}
