package me.stormma.support.banner;

/**
 * @author stormma
 * @date 2017/09/11
 */
public class SpringApplicationBannerPrinter {

    static final String BANNER_LOCATION_PROPERTY = "banner.location";

    static final String DEFAULT_BANNER_LOCATION = "banner.txt";

    private static final Banner DEFAULT_BANNER = new StormApplicationBanner();
}
