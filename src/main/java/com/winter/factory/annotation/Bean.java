package com.winter.factory.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Bean {

	String[] value() default {};

	String[] name() default {};

	boolean autowireCandidate() default true;

	String initMethod() default "";

	String destroyMethod() default "(inferred)";
}
