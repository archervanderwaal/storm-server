package me.stormma.core.http.annotation;

import me.stormma.core.http.enums.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author stormma
 * @date 2017/8/13.
 * @description 注解一个方法为api
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Api {
    /**
     * url
     */
    String url() default "";

    /**
     * request method
     */
    RequestMethod method() default RequestMethod.GET;
}
