package me.stormma.support.util;

import me.stormma.support.utils.AppUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author stormma
 * @date 2017/8/13.
 */
public class AppUtilsTest {

    private Logger logger = LoggerFactory.getLogger(AppUtils.class);
    @Test
    public void testGetUuid() {
        long uuid = AppUtils.getUuid();
        logger.info("测试结果{}", uuid);
    }
}
