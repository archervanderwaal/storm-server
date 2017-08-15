package me.stormma.converter;

import me.stormma.http.converter.impl.StringToNumberConverter;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author stormma
 * @date 2017/8/15.
 */
public class StringToNumberConverterTest {

    private static final Logger logger = LoggerFactory.getLogger(StringToNumberConverterTest.class);

    @Test
    public void testConvert() {
        String source = "122333";
        logger.info("测试结果:{}", new StringToNumberConverter(Integer.class).convert(source));
    }
}
