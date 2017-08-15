package me.stormma.http.handler.invoker.impl;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import me.stormma.exception.StormServerException;
import me.stormma.http.annotation.RequestParam;
import me.stormma.http.converter.ConverterCenter;
import me.stormma.http.enums.RequestMethod;
import me.stormma.http.handler.Handler;
import me.stormma.http.handler.invoker.HandlerInvoker;
import me.stormma.http.model.HttpContext;
import me.stormma.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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
    public void invoke(HttpContext context, Handler handler) throws Exception {
        Class<?> controllerClass = handler.getControllerClass();
        Method method = handler.getMethod();
        RequestMethod requestMethod = handler.getRequestMethod();
        Object controllerInstance = controllerClass.newInstance();

        //
        List<Object> methodParams = listMethodParam(context, handler);
    }

    private List<Object> listMethodParam(HttpContext context, Handler handler) {
        Map<Class, Class> converterMap = ConverterCenter.convertMap;
        List<Object> params = new ArrayList<Object>();
        Method method = handler.getMethod();
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            //如果是RequestParam
            if (!Objects.equal(null, parameter.getAnnotation(RequestParam.class))) {
                RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
                String name = requestParam.name();
                boolean required = requestParam.required();
                if (StringUtils.isEmpty(name)) {
                    logger.error("RequestParam annotation is Illegal, don't have name property");
                    throw new RuntimeException("RequestParam annotation is Illegal, don't have name property");
                }
                //必需的参数不存在
                if (Objects.equal(null, context.params.get(name)) && required) {
                    logger.error("the required param: {} not found", name);
                    throw new RuntimeException(String.format("the required param: %s not found", name));
                }
                Class<?> parameterType = parameter.getType();
                //如果query类型的参数是List
                if (context.params.get(name) instanceof List) {
                    List valueList = (List) context.params.get(name);
                    //判断接收参数的类型是String[]还是List[]
                    if (parameterType == String[].class) {
                        String[] value = (String[]) valueList.toArray();
                        params.add(value);
                    } else if (List.class.isAssignableFrom(parameterType)) {

                        params.add(valueList);
                    } else if (Set.class.isAssignableFrom(parameterType)) {
                        //Set<S
                    }
                }

                //如果参数的类型是String，那么直接可以赋值，这块想不到什么好的处理方式了，先这么写着吧。
                if (parameterType == String.class) {
                    params.add(context.params.get(name));
                    continue;
                }
                //参数类型不是String,判断是否是转换器中可以转换的类型
                for (Class type : converterMap.keySet()) {
                    String value = (String) context.params.get(name);
                    if (type.isAssignableFrom(parameterType)) {
                        try {
                            Class converterClass = converterMap.get(type);
                            Object result = converterClass.getMethod("convert", String.class)
                                                                        .invoke(converterClass.newInstance(), value);
                            params.add(result);
                        } catch (Exception e) {
                            logger.error("storm server get converter's convert method failed: {}", e.getMessage());
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
        return null;
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

    private void checkParams(Method actionMethod, List<Object> actionMethodParamList) {
        // 判断 Action 方法参数的个数是否匹配
        Class<?>[] actionMethodParameterTypes = actionMethod.getParameterTypes();
        if (actionMethodParameterTypes.length != actionMethodParamList.size()) {
            String message = String.format("因为参数个数不匹配，所以无法调用 Action 方法！原始参数个数：%d，实际参数个数：%d", actionMethodParameterTypes.length, actionMethodParamList.size());
            throw new RuntimeException(message);
        }
    }
}
