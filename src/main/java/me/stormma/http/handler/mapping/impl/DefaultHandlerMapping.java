package me.stormma.http.handler.mapping.impl;

import com.google.common.base.Objects;
import me.stormma.http.enums.RequestMethod;
import me.stormma.http.handler.Handler;
import me.stormma.http.handler.mapping.HandlerMapping;
import me.stormma.http.helper.ApplicationHelper;

import java.util.Map;

/**
 * @author stormma
 * @date 2017/8/15.
 * @description default handler mapping
 */
public class DefaultHandlerMapping implements HandlerMapping {

    private static DefaultHandlerMapping instance;

    private DefaultHandlerMapping() {
    }

    public static HandlerMapping getInstance() {
        if (Objects.equal(null, instance)) {
            synchronized (DefaultHandlerMapping.class) {
                if (Objects.equal(null, instance)) {
                    instance = new DefaultHandlerMapping();
                }
            }
        }
        return instance;
    }

    /**
     * @param requestMethod
     * @param url
     * @return
     * @description 获得handler
     */
    @Override
    public Handler getHandler(RequestMethod requestMethod, String url) {
        Map<String, Handler> apiMap = ApplicationHelper.listApiMap();
        Handler handler = apiMap.get(url);
        if (Objects.equal(null, handler.getMethod())) {
            //默认支持GET和POST方法
            if (Objects.equal(requestMethod, RequestMethod.GET) || Objects.equal(requestMethod, RequestMethod.POST)) {
                return handler;
            }
        } else {
            if (Objects.equal(requestMethod, handler.getMethod())) {
                return handler;
            }
        }
        return null;
    }
}
