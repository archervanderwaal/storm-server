package me.stormma.http.handler.invoker.impl;

import com.google.common.base.Objects;
import me.stormma.http.enums.RequestMethod;
import me.stormma.http.handler.Handler;
import me.stormma.http.handler.invoker.HandlerInvoker;
import me.stormma.http.model.HttpContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author stormma
 * @date 2017/8/14.
 * @description 默认的Handler调用器
 */
public class DefaultHandleInvoker implements HandlerInvoker {

    private static DefaultHandleInvoker instance;

    private DefaultHandleInvoker() {
    }

    public static HandlerInvoker getInstance() {
        if (Objects.equal(null, instance)) {
            synchronized (DefaultHandleInvoker.class) {
                if (Objects.equal(null, instance)) {
                    instance = new DefaultHandleInvoker();
                }
            }
        }
        return instance;
    }

    /**
     * @param context
     * @param handler
     * @description invoke handler
     */
    @Override
    public void invoke(HttpContext context, Handler handler) throws Exception {
        Class<?> controllerClass = handler.getControllerClass();
        Method method = handler.getMethod();
        RequestMethod requestMethod = handler.getRequestMethod();
        Object controllerInstance = controllerClass.newInstance();

    }

    private List<Object> listMethodParam(Handler handler) {
        List<Object> params = new ArrayList<Object>();
        Method method = handler.getMethod();
        Class<?>[] paramTypes = method.getParameterTypes();
        return null;
    }
}
