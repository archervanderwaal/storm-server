package me.stormma.converter;

import me.stormma.http.converter.impl.StringToBooleanConverter;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author stormma
 * @date 2017/8/15.
 */
public class StringToBooleanConverterTest {

    private static final Logger logger = LoggerFactory.getLogger(StringToBooleanConverterTest.class);

    @Test
    public void testConvert() {
        String source = "on";
        logger.info("测试结果:{}", new StringToBooleanConverter().convert(source));
    }
}
