package com.winter.property;

import java.io.InputStream;
import java.lang.reflect.AnnotatedElement;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Properties;

public class PropertyFileReader {

	private static final String WINTER_PROFILES_ACTIVE = "winter.profiles.active";
	private static Properties properties = new Properties();

	private static void readPropertyFile() {
		String profile = null != System.getProperty(WINTER_PROFILES_ACTIVE)
				? "."+System.getProperty(WINTER_PROFILES_ACTIVE)
				: "";
		String propertyFile = new StringBuilder().append("application").append(profile).append(".properties")
				.toString();

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

	@SuppressWarnings("unchecked")
	public static <T> T  getProperty(String key, Class<?> type) {
		String value = (String) properties.get(key);
		if(type== Double.class) {
			return (T) Double.valueOf(value);
		}
		else if(type == Integer.class) {
			return (T) Integer.valueOf(value);
		}
		else if(type == Long.class) {
			return (T) Long.valueOf(value);
		}
		else if(type == Short.class) {
			return (T) Short.valueOf(value);
		}
		else if(type == Float.class) {
			return (T) Float.valueOf(value);
		}
		else if(type == Byte.class) {
			return (T) Byte.valueOf(value);
		}
		else if(Boolean.class == type) {
			return (T) Boolean.valueOf( Boolean.parseBoolean(value));
		}
		return (T) (value);
	}

	private PropertyFileReader() {
	}

}
