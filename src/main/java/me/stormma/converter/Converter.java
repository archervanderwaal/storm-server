package me.stormma.converter;

import me.stormma.exception.StormServerException;

/**
 * @author stormma
 * @date 2017/8/14.
 * @description 转换器接口
 */
public interface Converter<S, T> {
    /**
     * @description 转换
     * @param source
     * @return
     */
    T convert(S source) throws StormServerException;
}
