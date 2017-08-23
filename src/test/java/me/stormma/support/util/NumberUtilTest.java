package me.stormma.support.util;

import me.stormma.exception.StormServerException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author stormma
 * @date 2017/8/15.
 */
public class NumberUtilTest {

    private static final Logger logger = LoggerFactory.getLogger(NumberUtil.class);

//    @Test
    public void testParseNumber(Integer[] s) throws IllegalAccessException, InstantiationException, StormServerException {
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
        System.out.println("2017-12-31".matches("^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$"));
    }

}
