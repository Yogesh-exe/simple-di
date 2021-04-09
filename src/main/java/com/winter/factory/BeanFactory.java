package com.winter.factory;

import java.util.Objects;

public interface BeanFactory {
	
	public default Object getBean(final Class<?> beanClass) {
		Objects.requireNonNull(beanClass, "beanClass not provided");
		return BeanStore.getBean(beanClass);
	}
	
	public Object createBean(Class<?> bean);

	public default void putBean(Class<?> beanClass, Object bean) {
		BeanStore.addBean(beanClass, bean);
	}

}
