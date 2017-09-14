package me.stormma.support.util;

import org.junit.Test;

import java.util.concurrent.Callable;

/**
 * @author stormma
 * @date 2017/09/12
 */
public class CallUtilsTest {
    public String getValue(String prefix, Callable<String> call) throws Exception {
        String value = call.call();
        return prefix + "/" + value;
    }

    @Test
    public void test() throws Exception {
        String result = getValue("mac", () -> System.getProperty("user.dir"));
        System.out.println(result);
    }
}
