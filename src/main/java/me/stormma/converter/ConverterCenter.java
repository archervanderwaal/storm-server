package me.stormma.converter;

import me.stormma.converter.impl.DefaultStringToDateConverter;
import me.stormma.converter.impl.StringToBooleanConverter;
import me.stormma.converter.impl.StringToNumberConverter;
import me.stormma.exception.StormServerException;

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

    /**
     * @descrption 添加转换器
     * @param clazz
     * @param converterClass
     */
    public static void addConverter(Class clazz, Class converterClass) throws StormServerException {
        if (Objects.equals(null, clazz) || Objects.equals(null, converterClass)) {
            throw new StormServerException("clazz, converterClass is null, please validate param type");
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
