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
        ServerConfig.init("/conf/smart.properties");
    }

    @Test
    public void testGetConfigField() {
        System.out.println(ServerConfig.REQUEST_ID);
        System.out.println(ServerConfig.PORT);
        System.out.println(ServerConfig.SERVER_ID);
    }
}
