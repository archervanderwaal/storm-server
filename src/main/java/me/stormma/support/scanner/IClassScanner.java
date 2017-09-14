package me.stormma.support.scanner;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @description 类加载器接口
 * @author stormma 
 * @date 2017/8/22
 */
public interface IClassScanner {

    /**
     * @description 获取指定包名中的所有类
     * @param packageName
     * @return
     */
    Set<Class<?>> getClasses(String packageName);

    /**
     * @desription 获取指定包下的类，或者接口的实现类
     * @param packageName 
     * @param annotationClass
     * @return 
     */
    Set<Class<?>> getClassesByAnnotation(String packageName, Class<? extends Annotation> annotationClass);

    /**
     * @description 指定包下获取某个类的子类
     * @param packageName 
     * @param type
     * @return 
     */
    <T> Set<Class<? extends T>> getSubClassesOf(String packageName, Class<T> type);

    /**
     * @description classpath
     * @param type
     * @return
     */
    <T> Set<Class<? extends T>> getSubClassesOfClassPath(Class<T> type);
}
