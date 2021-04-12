package com.winter.factory;

import java.util.HashMap;
import java.util.Map;

public class BeanStore {
	
	private  Map<Class<?>, Object> beanBag = new HashMap<>();
	
	public Object getBean(final Class<?> beanClass) {
		if(beanClass == null) {
			throw new IllegalArgumentException("beanClass not provided");
		}
		return beanBag.get(beanClass);
	}
	
	public  void addBean(Class<?> beanClass, Object bean) {
		beanBag.put(beanClass, bean);
	}
	
	public boolean containsBean(Class<?> isThisBeanPresent) {
		return beanBag.containsKey(isThisBeanPresent);
	}
	

}
