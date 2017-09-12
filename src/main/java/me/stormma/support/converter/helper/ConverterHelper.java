package me.stormma.support.converter.helper;

import me.stormma.annotation.Converter;
import me.stormma.factory.InstancePool;
import me.stormma.support.converter.impl.DefaultStringToDateConverter;
import me.stormma.support.converter.impl.StringToBooleanConverter;
import me.stormma.support.converter.impl.StringToNumberConverter;
import me.stormma.support.scanner.IClassScanner;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @description 转换器帮助类
 * @author stormma
 * @date 2017/8/20
 */
public class ConverterHelper {

    /**
     * logback
     */
    private static final Logger logger = LoggerFactory.getLogger(ConverterHelper.class);

    /**
     * class scanner
     */
    private static IClassScanner classScanner = InstancePool.getClassScanner();

    /**
     * @description 初始化转换器
     * @param basePackageName
     */
    public static Map<Class<?>, Class<?>> initConverter(String basePackageName) {
        Map<Class<?>, Class<?>> converterMap = new HashMap<>();
        //添加默认的转换器
        converterMap.put(Number.class, StringToNumberConverter.class);
        converterMap.put(Boolean.class, StringToBooleanConverter.class);
        converterMap.put(Date.class, DefaultStringToDateConverter.class);
        Reflections reflections = new Reflections(basePackageName);
        //用户自定义的转转器
        Set<Class<?>> converterClasses = classScanner.getClassesByAnnotation(basePackageName, Converter.class);
        for (Class<?> clazz : converterClasses) {
            Converter converter = clazz.getAnnotation(Converter.class);
            Class<?> convertType = converter.value();
            converterMap.put(convertType, clazz);
        }
        return converterMap;
    }
}
