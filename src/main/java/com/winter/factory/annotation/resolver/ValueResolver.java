package com.winter.factory.annotation.resolver;

import java.lang.reflect.Field;

import com.winter.factory.annotation.Value;
import com.winter.property.PropertyFileReader;

public class ValueResolver {

	private PropertyFileReader propertyFileReader;

	public Object getFieldValue(Field field) {
		Value annotation;
		if((annotation = field.getAnnotation(Value.class)) != null) {
			String value = annotation.value();
			return propertyFileReader.getProperty(value);
		}
		return null;
	}

	public ValueResolver(PropertyFileReader propertyFileReader) {
		super();
		this.propertyFileReader = propertyFileReader;
	}

	public ValueResolver() {
		super();
		this.propertyFileReader = new PropertyFileReader();
	}

}
