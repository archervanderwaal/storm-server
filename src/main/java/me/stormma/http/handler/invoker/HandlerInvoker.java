package me.stormma.http.handler.invoker;

import me.stormma.http.handler.Handler;
import me.stormma.http.model.HttpContext;

/**
 * @author stormma
 * @date 2017/8/14.
 * @description handler invoker interfaces
 */
public interface HandlerInvoker {

    /**
     * @description invoke handler
     * @param context
     * @param handler
     * @return 调用结果
     */
    Object invoke(HttpContext context, Handler handler) throws Exception;
}
