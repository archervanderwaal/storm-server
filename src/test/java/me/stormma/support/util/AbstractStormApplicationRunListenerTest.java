package me.stormma.support.util;

import me.stormma.ansi.AnsiOutput;
import me.stormma.support.listener.StormApplicationBannerRunListener;
import me.stormma.support.listener.StormApplicationEnvironmentRunListener;
import me.stormma.support.listener.StormApplicationContextRunListener;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author stormma
 * @date 2017/09/13
 */
public class AbstractStormApplicationRunListenerTest {

    private static final Logger logger = LoggerFactory.getLogger(AbstractStormApplicationRunListenerTest.class);

    @Before
    public void ansi() {
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
    }

    @Test
    public void test() {
        StormApplicationEnvironmentRunListener listener1 = new StormApplicationEnvironmentRunListener();
        StormApplicationBannerRunListener listener2 = new StormApplicationBannerRunListener();
        StormApplicationContextRunListener listener3 = new StormApplicationContextRunListener();
        logger.info("1==>{}", listener1.getStormApplicationRunListenerStartOrder());
        logger.info("2==>{}", listener2.getStormApplicationRunListenerStartOrder());
        logger.info("3==>{}", listener3.getStormApplicationRunListenerStartOrder());
    }
}
