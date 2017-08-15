package me.stormma.http.converter;

import com.google.common.base.Objects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author stormma
 * @date 2017/8/14.
 * @description 转换器接口 S==>T
 */
public interface Converter<S, T> {
    /**
     * @description 转换
     * @param source
     * @return
     */
    T convert(S source);
}
