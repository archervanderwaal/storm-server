package me.stormma.support.util;

import java.util.Collection;
import java.util.Objects;

/**
 * @author stormma
 * @date 2017/8/20
 * @description Collection util
 */
public class CollectionUtils {

    /**
     * @description 判断是否为空
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection collection) {
        return Objects.equals(null, collection) ? true : collection.size() == 0;
    }

    /**
     * @description 判断非空
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }
}
