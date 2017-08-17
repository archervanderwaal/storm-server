package me.stormma.http.handler.invoker.impl;

import com.google.common.base.Objects;
import me.stormma.exception.StormServerException;
import me.stormma.http.enums.RequestMethod;
import me.stormma.http.handler.Handler;
import me.stormma.http.handler.invoker.HandlerInvoker;
import me.stormma.http.model.HttpContext;
import me.stormma.http.util.BindingParameterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.util.*;

/**
 * @author stormma
 * @date 2017/8/14.
 * @description 默认的Handler调用器
 */
public class DefaultHandleInvoker implements HandlerInvoker {

    private static final Logger logger = LoggerFactory.getLogger(DefaultHandleInvoker.class);

    /**
     * @param context
     * @param handler
     * @description invoke handler
     */
    @Override
    public Object invoke(HttpContext context, Handler handler) throws Exception {
        Class<?> controllerClass = handler.getControllerClass();
        Method method = handler.getMethod();
        RequestMethod requestMethod = handler.getRequestMethod();
        Object controllerInstance = controllerClass.newInstance();
        List<Object> methodParams = listMethodParam(context, handler);
        return invokeMethod(method, controllerInstance, methodParams);
    }

    /**
     * @description 自动绑定参数
     * @param context
     * @param handler
     * @return
     * @throws StormServerException
     */
    private List<Object> listMethodParam(HttpContext context, Handler handler) throws StormServerException {
        List<Object> result = new ArrayList<>();
        Parameter[] parameters = handler.getMethod().getParameters();
        for (Parameter parameter : parameters) {
            result.add(BindingParameterUtils.bindParam(parameter, context));
        }
        return result;
    }

    /**
     * @description 通过反射执行方法
     * @param method
     * @param controllerInstance
     * @param params
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private Object invokeMethod (Method method, Object controllerInstance, List<Object> params)
                                                        throws IllegalAccessException, InvocationTargetException {
        method.setAccessible(true);
        if (!Objects.equal(null, params)) {
            return method.invoke(controllerInstance, params.toArray());
        }
        return method.invoke(controllerInstance);
    }
}
