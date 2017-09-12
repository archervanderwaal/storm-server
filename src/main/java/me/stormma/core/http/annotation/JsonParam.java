package me.stormma.core.http.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author stormma
 * @date 2017/8/14.
 * @description json param annotation
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonParam {
    /**
     * 是否是必须
     */
    boolean required() default true;
}
