package me.stormma.support.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class Utils..
 * @author stormma
 * @date 2017/09/12
 */
public class ClassUtils {

    private static final Logger logger = LoggerFactory.getLogger(ClassUtils.class);

    /**
     * 获取调用方Class
     * @param index
     * @return
     */
    public static Class<?> getPreCallClass(int index) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement a = (StackTraceElement) stackTrace[index];
        String className = a.getClassName();
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.error("reflect class: {} not found. {}", className, e.getMessage());
        }
        return clazz;
    }
}
