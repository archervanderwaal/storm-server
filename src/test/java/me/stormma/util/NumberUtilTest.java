package me.stormma.util;

import me.stormma.http.annotation.JsonParam;
import me.stormma.http.annotation.PathVariable;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author stormma
 * @date 2017/8/15.
 */
public class NumberUtilTest {

    private static final Logger logger = LoggerFactory.getLogger(NumberUtil.class);

//    @Test
    public void testParseNumber(String[] ls, List<String> ids) throws IllegalAccessException, InstantiationException {
        String number = "1234";
        logger.info("测试结果: {}", NumberUtil.parseNumber(number, Double.class));
        logger.info("{}", String.class.isAssignableFrom(String.class));
    }

    @Test
    public void test() throws NoSuchMethodException {
        Method method = NumberUtilTest.class.getMethod("testParseNumber", String[].class, List.class);
        Type[] types = method.getGenericParameterTypes();
        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                Type[] typesss = ((ParameterizedType) type).getActualTypeArguments();
                for (Type type1 : typesss) {
                    System.out.println(type1);
                }
            }
            System.out.println(type);
        }
    }
}
