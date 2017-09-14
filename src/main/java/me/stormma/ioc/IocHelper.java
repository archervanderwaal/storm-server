package me.stormma.ioc;


import me.stormma.factory.InstancePool;
import me.stormma.fault.InitializationError;
import me.stormma.ioc.annotation.AutoWired;
import me.stormma.ioc.annotation.Component;
import me.stormma.ioc.annotation.Service;
import me.stormma.support.helper.ApplicationHelper;
import me.stormma.support.scanner.IClassScanner;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author stormma
 * @description ioc
 * @date 2017/8/21
 */
public class IocHelper {

    /**
     * class scanner
     */
    private static final IClassScanner classScanner = InstancePool.getClassScanner();

    /**
     * @description 初始化bean的实例依赖
     */
    public static void initBean(String packageName) {
        for (Map.Entry<Class<?>, Object> entry : ApplicationHelper.beanMap.entrySet()) {
            Class<?> beanClass = entry.getKey();
            Object instance = entry.getValue();
            Field[] fields = beanClass.getDeclaredFields();
            if (!ArrayUtils.isEmpty(fields)) {
                for (Field field : fields) {
                    boolean isInjectSuccess = false;
                    if (field.isAnnotationPresent(AutoWired.class)) {
                        Class fieldClass = field.getType();
                        Set<Class> implementClasses = classScanner.getSubClassesOf(packageName, fieldClass);
                        if (CollectionUtils.isNotEmpty(implementClasses)) {
                            if (Objects.equals(1, implementClasses.size())) {
                                //如果要注入的类型只有子类只有一个，那么取出这个类型的instance
                                Object fieldClassInstance = ApplicationHelper.beanMap.get(implementClasses.toArray()[0]);
                                if (fieldClassInstance != null) {
                                    //public
                                    field.setAccessible(true);
                                    try {
                                        //注入
                                        field.set(instance, fieldClassInstance);
                                        isInjectSuccess = true;
                                    } catch (IllegalAccessException e) {
                                        throw new InitializationError("IOC init class field instance failed!", e);
                                    }
                                } else {
                                    throw new InitializationError(String.format("inject %s => %s failed!",
                                            beanClass.getSimpleName(), fieldClass.getSimpleName()));
                                }
                            } else {
                                AutoWired autoWired = field.getAnnotation(AutoWired.class);
                                //指定要注入的实现
                                String value = autoWired.value();
                                if (StringUtils.isEmpty(value)) {
                                    throw new InitializationError(String.format("the %s field has multiple " +
                                                    "implementation classes, which do not specify which",
                                            fieldClass.getSimpleName()));
                                }
                                for (Class<?> implementClass : implementClasses) {
                                    if (isInjectSuccess) {
                                        break;
                                    }
                                    if (ApplicationHelper.beanMap.containsKey(implementClass)) {
                                        Service service = implementClass.getAnnotation(Service.class);
                                        //如果这个bean是service
                                        if (service != null) {
                                            //判断是不是要注入的实现
                                            if (value.equals(service.value())) {
                                                Object fieldImplClassInstance = ApplicationHelper.beanMap.get(implementClass);
                                                //public
                                                field.setAccessible(true);
                                                try {
                                                    //注入
                                                    field.set(instance, fieldImplClassInstance);
                                                    isInjectSuccess = true;
                                                } catch (IllegalAccessException e) {
                                                    throw new InitializationError("IOC init class field instance failed!", e);
                                                }
                                            }
                                        } else {
                                            Component component = implementClass.getAnnotation(Component.class);
                                            if (component != null) {
                                                //判断是不是要注入的实现
                                                if (value.equals(component.value())) {
                                                    Object fieldImplClassInstance = ApplicationHelper.beanMap.get(implementClass);
                                                    //public
                                                    field.setAccessible(true);
                                                    try {
                                                        //注入
                                                        field.set(instance, fieldImplClassInstance);
                                                        isInjectSuccess = true;
                                                    } catch (IllegalAccessException e) {
                                                        throw new InitializationError("IOC init class field instance failed!", e);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (isInjectSuccess) {
                            ApplicationHelper.beanMap.put(beanClass, instance);
                            continue;
                        }
                        throw new InitializationError(String.format("no class instance can inject to %s", field.getType()));
                    }
                }
            }
        }
    }
}
