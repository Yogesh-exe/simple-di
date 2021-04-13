package com.winter.factory;

import java.util.Objects;

public abstract class AbstractBeanFactory implements BeanFactory {
	protected BeanStore beanStore = new BeanStore();

	@Override
	public <T> T getBean(final Class<?> beanClass) {
		Objects.requireNonNull(beanClass, "beanClass not provided");
		return  (T) beanClass.cast(beanStore.getBean(beanClass));
	}

	@Override
	public void putBean(Class<?> beanClass, Object bean) {
		beanStore.addBean(beanClass, bean);
	}
	
}
