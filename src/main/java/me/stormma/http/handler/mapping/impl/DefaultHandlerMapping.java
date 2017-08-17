package me.stormma.http.handler.mapping.impl;

import com.google.common.base.Objects;
import me.stormma.http.enums.RequestMethod;
import me.stormma.http.handler.Handler;
import me.stormma.http.handler.mapping.HandlerMapping;
import me.stormma.http.helper.ApplicationHelper;
import me.stormma.http.model.HttpContext;

import java.util.Map;

/**
 * @author stormma
 * @date 2017/8/15.
 * @description default handler mapping
 */
public class DefaultHandlerMapping implements HandlerMapping {

    private static final Map<String, Handler> apiMap = ApplicationHelper.listApiMap();

    /**
     * @param context
     * @return
     * @description 获得handler
     */
    @Override
    public Handler getHandler(HttpContext context) {
        Handler handler = apiMap.get(context.requestPath);
        if (Objects.equal(null, handler)) {
            return null;
        }
        if (Objects.equal(null, handler.getMethod())) {
            //默认支持GET和POST方法
            if (Objects.equal(context.requestMethod, RequestMethod.GET) ||
                                                        Objects.equal(context.requestMethod, RequestMethod.POST)) {
                return handler;
            }
        } else {
            if (Objects.equal(context.requestMethod, handler.getRequestMethod())) {
                return handler;
            }
        }
        return null;
    }

    /**
     * @description 验证requestPath下是否有资源
     * @param context
     * @return
     */
    @Override
    public boolean validateRequestPath(HttpContext context) {
        return Objects.equal(null, apiMap.get(context.requestPath)) ? false : true;
    }
}
