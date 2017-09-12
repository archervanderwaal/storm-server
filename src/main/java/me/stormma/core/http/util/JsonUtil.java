package me.stormma.core.http.util;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author stormma
 * @date 2017/8/14.
 * @description json util
 */
public class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * @description Java对象转换成字符串
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String objConvert2JsonStr(T obj) {
        String jsonStr;
        try {
            jsonStr = objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            logger.error("java object convert to json string failed: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return jsonStr;
    }

    /**
     * @description json string convert 2 java object
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T jsonStrConvert2Obj(String json, Class<T> type) {
        T obj;
        try {
            obj = objectMapper.readValue(json, type);
        } catch (Exception e) {
            logger.error("json string convert to java object failed: {}", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return obj;
    }
}

