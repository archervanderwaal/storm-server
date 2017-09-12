package me.stormma.support.utils;

import java.util.UUID;

/**
 * @author stormma
 * @date 2017/8/13.
 * @description 常用工具类
 */
public class AppUtils {

    public static long getUuid() {
        return UUID.randomUUID().getMostSignificantBits();
    }
}
