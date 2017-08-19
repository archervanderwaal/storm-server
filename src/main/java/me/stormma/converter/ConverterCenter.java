package me.stormma.converter;

import me.stormma.converter.impl.DefaultStringToDateConverter;
import me.stormma.converter.impl.StringToBooleanConverter;
import me.stormma.converter.impl.StringToNumberConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author stormma
 * @date 2017/8/15.
 */
public class ConverterCenter {

    public static Map<Class, Class> convertMap = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(ConverterCenter.class);

    /**
     * @descrption 添加转换器
     * @param clazz
     * @param converterClass
     */
    public static void addConverter(Class clazz, Class converterClass) {
        if (Objects.equals(null, clazz) || Objects.equals(null, converterClass)) {
            logger.error("clazz, converterClass is null, please validate param type");
            throw new RuntimeException("clazz, converterClass is null, please validate param type");
        }
        convertMap.put(clazz, converterClass);
    }

    /**
     * @description 添加默认转换器
     */
    public static void init() {
        convertMap.put(Number.class, StringToNumberConverter.class);
        convertMap.put(Boolean.class, StringToBooleanConverter.class);
        convertMap.put(Date.class, DefaultStringToDateConverter.class);
    }
}
