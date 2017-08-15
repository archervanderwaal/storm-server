package me.stormma.http.converter;

import me.stormma.exception.NullParamException;
import me.stormma.http.converter.impl.StringToBooleanConverter;
import me.stormma.http.converter.impl.StringToNumberConverter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author stormma
 * @date 2017/8/15.
 */
public class ConverterCenter {

    //convert map
    public static Map<Class, Class> convertMap = new HashMap<Class, Class>();

    /**
     * @descrption 添加转换器
     * @param clazz
     * @param converterClass
     */
    public static void addConverter(Class clazz, Class converterClass) throws NullParamException {
        if (Objects.equals(null, clazz) || Objects.equals(null, converterClass)) {
            throw new NullParamException("clazz, converterClass is null, please validate param type");
        }
        convertMap.put(clazz, converterClass);
    }

    /**
     * @description 添加默认转换器
     */
    public static void init() {
        convertMap.put(Number.class, StringToNumberConverter.class);
        convertMap.put(Boolean.class, StringToBooleanConverter.class);
    }
}
