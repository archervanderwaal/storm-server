package me.stormma.http.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author stormma
 * @date 2017/8/13.
 * @description 注解一个类为请求处理类
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
    /**
     * url
     */
    String value() default "";
}
