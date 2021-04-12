package com.winter.factory.annotation.resolver;

import java.lang.reflect.AnnotatedElement;

import com.winter.factory.annotation.Value;
import com.winter.property.PropertyFileReader;

public class ValueResolver {

	public static Object getFieldValue(AnnotatedElement field) {
		Value annotation;
		if ((annotation = field.getAnnotation(Value.class)) != null) {
			String value = annotation.value();
			return PropertyFileReader.getProperty(value);
		}
		return null;
	}

}
