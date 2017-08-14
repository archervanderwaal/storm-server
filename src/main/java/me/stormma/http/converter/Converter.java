package me.stormma.http.converter;

import com.google.common.base.Objects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author stormma
 * @date 2017/8/14.
 * @description 转换器
 */
public class Converter {

    public static Object convert(String desc, Class clazz) throws ParseException {
        if (Objects.equal(null, desc)) {
            return null;
        }
        if (clazz == Integer.class || clazz == int.class) {
            return Integer.parseInt(desc);
        } else if (clazz == Boolean.class || clazz == boolean.class) {
            return desc.equalsIgnoreCase("true") ? true : false;
        } else if (clazz == Date.class) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-HH-mm");
            return simpleDateFormat.parse(desc);
        }
        return desc;
    }
}
