package com.winter.factory;

import java.util.HashMap;
import java.util.Map;

public class BeanStore {
	
	private static Map<Class<?>, Object> beanBag = new HashMap<>();
	
	public static Object getBean(final Class<?> beanClass) {
		if(beanClass == null) {
			throw new IllegalArgumentException("beanClass not provided");
		}
		return beanBag.get(beanClass);
	}
	
	public static void addBean(Class<?> beanClass, Object bean) {
		beanBag.put(beanClass, bean);
	}
	
	public static boolean containsBean(Class<?> isThisBeanPresent) {
		return beanBag.containsKey(isThisBeanPresent);
	}
	
	private BeanStore() {
			throw new IllegalStateException("utility class");
	}
}
