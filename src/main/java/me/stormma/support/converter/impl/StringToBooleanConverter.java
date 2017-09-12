package me.stormma.support.converter.impl;

import me.stormma.support.converter.Converter;
import me.stormma.exception.StormServerException;
import me.stormma.support.utils.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @author stormma
 * @date 2017/8/15.
 * @descrioption 字符串转boolean
 */
public class StringToBooleanConverter implements Converter<String, Boolean> {

    private static final Set<String> trueValues = new HashSet(4);
    private static final Set<String> falseValues = new HashSet(4);

    /**
     * @param source
     * @return
     * @description 转换
     */
    @Override
    public Boolean convert(String source) throws StormServerException {
        String value = source.trim();
        if(StringUtils.isEmpty(value)) {
            return null;
        } else {
            value = value.toLowerCase();
            if(trueValues.contains(value)) {
                return Boolean.TRUE;
            } else if(falseValues.contains(value)) {
                return Boolean.FALSE;
            } else {
                throw new StormServerException(String.format("Invalid boolean value ' %s '", source));
            }
        }
    }

    static {
        trueValues.add("true");
        trueValues.add("on");
        trueValues.add("yes");
        trueValues.add("1");
        falseValues.add("false");
        falseValues.add("off");
        falseValues.add("no");
        falseValues.add("0");
    }
}
