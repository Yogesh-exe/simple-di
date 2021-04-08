package com.yogesh.simple_di.factory.annotation.resolver;

import java.lang.reflect.Field;

import com.yogesh.simple_di.factory.annotation.Value;
import com.yogesh.simple_di.property.PropertyFileReader;

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


}
