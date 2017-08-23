package me.stormma.support.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author stormma
 * @date 2017/8/13.
 */
public class AppUtilTest {

    private Logger logger = LoggerFactory.getLogger(AppUtil.class);
    @Test
    public void testGetUuid() {
        long uuid = AppUtil.getUuid();
        logger.info("测试结果{}", uuid);
    }
}
