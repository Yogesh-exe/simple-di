package com.winter.factory;

import java.util.Collection;
import java.util.Objects;

public interface BeanFactory {

	default Object getBean(final Class<?> beanClass) {
		Objects.requireNonNull(beanClass, "beanClass not provided");
		return BeanStore.getBean(beanClass);
	}

	Object createBean(Class<?> bean);

	default void putBean(Class<?> beanClass, Object bean) {
		BeanStore.addBean(beanClass, bean);
	}

	default void putAll(Collection<Object> beanCollection) {
		beanCollection.forEach(c -> putBean(c.getClass(), c));
	}

}
