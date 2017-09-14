package me.stormma.config;

import me.stormma.exception.ConfigFileNotFoundException;
import org.junit.Before;
import org.junit.Test;

/**
 * @author stormma
 * @date 2017/8/13.
 */
public class ServerConfigTest {

    @Before
    public void initConfig() throws ConfigFileNotFoundException {
        //init config
//        StormApplicationConfig.init("/conf/storm.properties");
    }

    @Test
    public void testGetConfigField() {
//        System.out.println(StormApplicationConfig.PORT);
//        System.out.println(StormApplicationConfig.SERVER_ID);
    }
}
