package me.stormma.support.util;

import java.util.UUID;

/**
 * @author stormma
 * @date 2017/8/13.
 * @description 常用工具类
 */
public class AppUtil {

    public static long getUuid() {
        return UUID.randomUUID().getMostSignificantBits();
    }
}
