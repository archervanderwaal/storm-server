package me.stormma.support.scanner.impl;

import me.stormma.support.scanner.IClassScanner;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Set;

/**
 * @description 类扫描器的实现
 * @author stormma
 * @date 2017/8/22
 */
public class ClassScanner implements IClassScanner {

    private static final Logger logger = LoggerFactory.getLogger(ClassScanner.class);

    /**
     * @param packageName
     * @return
     * @description 获取指定包名中的所有类
     */
    @Override
    public Set<Class<?>> getClasses(String packageName) {
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        Set<String> allClassesType = reflections.getAllTypes();
        Set<Class<?>> classes = Collections.EMPTY_SET;
        for (String type : allClassesType) {
            try {
                classes.add(Class.forName(type));
            } catch (ClassNotFoundException e) {
                logger.error(e.toString());
            }
        }
        return classes;
    }

    /**
     * @param packageName
     * @param annotationClass
     * @return
     * @desription 获取指定包名中指定注解的相关类
     */
    @Override
    public Set<Class<?>> getClassesByAnnotation(String packageName, Class<? extends Annotation> annotationClass) {
        Reflections reflections = new Reflections(packageName);
        return reflections.getTypesAnnotatedWith(annotationClass);
    }

    /**
     * @param packageName
     * @param type
     * @return
     * @description
     */
    @Override
    public Set<Class<?>> getSubClassesOf(String packageName, Class<?> type) {
        Reflections reflections = new Reflections(packageName);
        return (Set<Class<?>>) reflections.getSubTypesOf(type);
    }
}
