package me.stormma.http.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Objects;

/**
 * @author stormma
 * @date 2017/8/14.
 * @description json util
 */
public class JsonUtil {
    /**
     * @description byte数组转换成java bean
     * @return
     */
    public static <T>  T byteArrayConvert2JavaBean (byte[] data, Class target) {
        if (Objects.equal(null, data) && Objects.equal(null, target)) {
            String jsonStr = new String(data);
            return (T) JSONObject.parseObject(jsonStr, target);
        }
        return null;
    }
}

