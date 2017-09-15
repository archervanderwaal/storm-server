package me.stormma.ioc.helper;

import me.stormma.exception.NoSuchBeanException;
import me.stormma.exception.StormServerException;
import me.stormma.factory.InstancePool;
import me.stormma.ioc.annotation.Component;
import me.stormma.ioc.annotation.Controller;
import me.stormma.ioc.annotation.Service;
import me.stormma.support.scanner.IClassScanner;
import me.stormma.support.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author stormma
 * @date 2017/8/20
 * @description bean helper， 管理bean的帮助类
 */
public class BeanHelper {
    /**
     * logback
     */
    private static Logger logger = LoggerFactory.getLogger(BeanHelper.class);

    /**
     * class scanner
     */
    private static IClassScanner classScanner = InstancePool.getClassScanner();

    /**
     * @description 初始化bean map
     * @param basePackageName
     */
    public static Map<Class<?>, Object> initBeanMap(String basePackageName) throws StormServerException {
        Map<Class<?>, Object> beanMap = new LinkedHashMap<>();
        Set<Class<?>> controllerClasses = classScanner.getClassesByAnnotation(basePackageName, Controller.class);
        Set<Class<?>> serviceClasses = classScanner.getClassesByAnnotation(basePackageName, Service.class);
        Set<Class<?>> componentClasses = classScanner.getClassesByAnnotation(basePackageName, Component.class);

        Set<Class<?>> beanClasses = new HashSet<>();
        if (CollectionUtils.isNotEmpty(controllerClasses)) {
            beanClasses.addAll(controllerClasses);
        }
        if (CollectionUtils.isNotEmpty(serviceClasses)) {
            beanClasses.addAll(serviceClasses);
        }
        if (CollectionUtils.isNotEmpty(componentClasses)) {
            beanClasses.addAll(componentClasses);
        }
        for (Class<?> clazz : beanClasses) {
            Object instance = null;
            try {
                instance = clazz.newInstance();
            } catch (Exception e) {
                logger.error("init {} bean class failed, message: {}", clazz, e.getMessage());
                throw new StormServerException(e);
            }
            beanMap.put(clazz, instance);
        }
        //logback beans
        logAllBeans(beanClasses);
        return beanMap;
    }

    /**
     * @descritpion 打印注册的bean
     * @param beans
     */
    private static void logAllBeans(Set<Class<?>> beans) {
        for (Class<?> bean: beans) {
            logger.info("bean register success ['{}']", bean);
        }
    }

    /**
     * @description 获取实例
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz, Map<Class<?>, Object> beanMap) throws NoSuchBeanException {
        if (clazz == null || !beanMap.containsKey(clazz)) {
            throw new NoSuchBeanException(String.format("no such bean named %s", clazz));
        }
        return (T) beanMap.get(clazz);
    }
}
