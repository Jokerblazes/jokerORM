package com.joker.jokerORM.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodType {
	String value() default "";
	
	//0 3代表自动生成sql 1代表用户传入的sql 2代表需要分页
	int type() default  0;
}
