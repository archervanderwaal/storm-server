package me.stormma.core.http.handler.mapping;

import me.stormma.core.http.handler.Handler;
import me.stormma.core.http.model.HttpContext;

/**
 * @author stormma
 * @date 2017/8/15.
 * @description handler mapping
 */
public interface HandlerMapping {

    /**
     * @description 获得handler
     * @param context
     * @return
     */
    Handler getHandler(HttpContext context);

    /**
     * @description 验证requestPath下是否有资源
     * @param context
     * @return
     */
    boolean validateRequestPath(HttpContext context);
}
