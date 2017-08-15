package me.stormma.factory;

import me.stormma.http.handler.invoker.HandlerInvoker;
import me.stormma.http.handler.mapping.HandlerMapping;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author stormma
 * @date 2017/8/15.
 */
public class InstancePoolTest {
    private final Logger logger = LoggerFactory.getLogger(InstancePool.class);
    @Test
    public void testInstance() {
        HandlerMapping handlerMapping = InstancePool.getDefaultHandlerMapping();
        HandlerInvoker handlerInvoker = InstancePool.getDefaultHandlerInvoker();
        logger.info("测试结果:{}==>{}", handlerMapping, handlerInvoker);
    }
}
