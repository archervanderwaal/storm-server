package me.stormma.support.util;

import org.junit.Test;

import java.util.Properties;

public class EnvironmentUtilsTest {
    @Test
    public void test() {
        Properties properties = System.getProperties();
        for (Object key: properties.keySet()) {
            System.out.println(key + "==>" + properties.get(key));
        }
        System.out.println(System.getProperty("user.dir"));
    }
}
