package com.annotation;

public @interface Uniqueness {
	Constraints constraints() default @Constraints(unique = true);
} // /:~
