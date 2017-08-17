package me.stormma.http.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author stormma
 * @date 2017/8/14.
 * @description request param annotation
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParam {
    /**
     *  参数名字
     */
    String name();

    /**
     * 是否是必须参数，默认是
     */
    boolean required() default true;
}
