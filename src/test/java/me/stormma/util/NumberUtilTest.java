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
    public void testParseNumber(Integer[] s) throws IllegalAccessException, InstantiationException {
        String number = "1234";
        logger.info("测试结果: {}", NumberUtil.parseNumber(number, Double.class));
        logger.info("{}", String.class.isAssignableFrom(String.class));
    }

    @Test
    public void test() throws NoSuchMethodException {
        Method method = NumberUtilTest.class.getMethod("testParseNumber", Integer[].class);
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            Class<?> paramType = parameter.getType();
            if (paramType == int[].class || paramType == Integer[].class) {
                System.out.println("sss: " + paramType);
            }
            System.out.println(paramType);
        }
    }
}
