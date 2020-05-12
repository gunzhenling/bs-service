package com.bs.payment.common.interceptor.jwt;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 允许匿名访问
 * 
 * @Author fanhang
 * @CreateAt 2018年6月26日-下午12:36:03
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AllowGuest {
	
}
