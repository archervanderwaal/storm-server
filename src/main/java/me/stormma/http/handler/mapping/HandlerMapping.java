package me.stormma.http.handler.mapping;

import me.stormma.http.enums.RequestMethod;
import me.stormma.http.handler.Handler;

/**
 * @author stormma
 * @date 2017/8/15.
 * @description handler mapping
 */
public interface HandlerMapping {

    /**
     * @description 获得handler
     * @param requestMethod
     * @param url
     * @return
     */
    Handler getHandler(RequestMethod requestMethod, String url);
}
