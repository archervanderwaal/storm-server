package me.stormma.util;

import me.stormma.exception.NullParamException;
import me.stormma.exception.StormServerException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

/**
 * @author stormma
 * @date 2017/8/15.
 * @description number工具类
 */
public class NumberUtil {

    /**
     * @param value
     * @return
     */
    private static boolean isHexNumber(String value) {
        int index = value.startsWith("-") ? 1 : 0;
        return value.startsWith("0x", index) || value.startsWith("0X", index)
                                                                                || value.startsWith("#", index);
    }

    /**
     * @param value
     * @return
     */
    private static BigInteger decodeBigInteger(String value) {
        int radix = 10;
        int index = 0;
        boolean negative = false;
        if(value.startsWith("-")) {
            negative = true;
            ++index;
        }
        if(!value.startsWith("0x", index) && !value.startsWith("0X", index)) {
            if(value.startsWith("#", index)) {
                ++index;
                radix = 16;
            } else if(value.startsWith("0", index) && value.length() > 1 + index) {
                ++index;
                radix = 8;
            }
        } else {
            index += 2;
            radix = 16;
        }

        BigInteger result = new BigInteger(value.substring(index), radix);
        return negative?result.negate():result;
    }

    /**
     * @description 解析Number
     * @param text
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T extends Number> T parseNumber(String text, Class<T> targetClass) throws StormServerException {
        String trimmed = StringUtils.trimAllWhitespace(text);
        if(Byte.class == targetClass) {
            return (T) (isHexNumber(trimmed) ? Byte.decode(trimmed) : Byte.valueOf(trimmed));
        } else if(Short.class == targetClass) {
            return (T) (isHexNumber(trimmed) ? Short.decode(trimmed) : Short.valueOf(trimmed));
        } else if(Integer.class == targetClass) {
            return (T) (isHexNumber(trimmed) ? Integer.decode(trimmed) : Integer.valueOf(trimmed));
        } else if(Long.class == targetClass) {
            return (T) (isHexNumber(trimmed)?Long.decode(trimmed):Long.valueOf(trimmed));
        } else if(BigInteger.class == targetClass) {
            return (T) (isHexNumber(trimmed)?decodeBigInteger(trimmed):new BigInteger(trimmed));
        } else if(Float.class == targetClass) {
            return (T) Float.valueOf(trimmed);
        } else if(Double.class == targetClass) {
            return (T) Double.valueOf(trimmed);
        } else if(BigDecimal.class != targetClass && Number.class != targetClass) {
            throw new StormServerException("Cannot convert String [" + text + "] to target class [" + targetClass.getName() + "]");
        } else {
            return (T) new BigDecimal(trimmed);
        }
    }
}
