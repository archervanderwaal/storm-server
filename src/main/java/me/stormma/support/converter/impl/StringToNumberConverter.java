package me.stormma.support.converter.impl;

import me.stormma.support.converter.Converter;
import me.stormma.exception.StormServerException;
import me.stormma.support.utils.NumberUtils;

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
    public T convert(String source) throws StormServerException {
        return Objects.equals(null, source.length()) ? null : NumberUtils.parseNumber(source, this.targetType);
    }

    public StringToNumberConverter (Class<T> targetType) {
        this.targetType = targetType;
    }
}
