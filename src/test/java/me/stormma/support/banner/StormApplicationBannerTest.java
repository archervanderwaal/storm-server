package me.stormma.support.banner;

import org.junit.Test;

import java.io.PrintStream;

/**
 * @author stormma
 * @date 2017/09/11
 */
public class StormApplicationBannerTest {
    @Test
    public void testPrintBanner() {
        Banner banner = new StormApplicationBanner();
        banner.printBanner(null, new PrintStream(System.out));
    }
}
