package me.stormma.converter.impl;

import me.stormma.converter.Converter;
import me.stormma.exception.StormServerException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description String convert 2 Date converter
 * @author stormma
 * @date 2017/8/17
 */
public class DefaultStringToDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) throws StormServerException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(source);
        } catch (ParseException e) {
            throw new StormServerException("string convert to date failed, because param is not valid.");
        }
        return date;
    }
}
