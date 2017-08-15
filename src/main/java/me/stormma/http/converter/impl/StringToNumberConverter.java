package me.stormma.http.converter.impl;

import me.stormma.http.converter.Converter;
import me.stormma.util.NumberUtil;

import java.util.Objects;

/**
 * @author stormma
 * @date 2017/8/15.
 */
public class StringToNumberConverter<T extends Number> implements Converter<String, T> {

    private final Class<T> targetType;

    /**
     * @description 转换
     * @param source
     * @return
     */
    @Override
    public T convert(String source) {
        return Objects.equals(null, source.length()) ? null : NumberUtil.parseNumber(source, this.targetType);
    }

    public StringToNumberConverter (Class<T> targetType) {
        this.targetType = targetType;
    }
}
