package me.stormma.http.handler.invoker.impl;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import me.stormma.exception.StormServerException;
import me.stormma.http.annotation.RequestParam;
import me.stormma.http.converter.ConverterCenter;
import me.stormma.http.converter.impl.StringToBooleanConverter;
import me.stormma.http.converter.impl.StringToNumberConverter;
import me.stormma.http.enums.RequestMethod;
import me.stormma.http.handler.Handler;
import me.stormma.http.handler.invoker.HandlerInvoker;
import me.stormma.http.model.HttpContext;
import me.stormma.util.StringUtils;
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
    public void invoke(HttpContext context, Handler handler) throws Exception {
        Class<?> controllerClass = handler.getControllerClass();
        Method method = handler.getMethod();
        RequestMethod requestMethod = handler.getRequestMethod();
        Object controllerInstance = controllerClass.newInstance();

        //
        List<Object> methodParams = listMethodParam(context, handler);
    }

    /**
     * @description 自动绑定参数
     * @param context
     * @param handler
     * @return
     * @throws StormServerException
     */
    private List<Object> listMethodParam(HttpContext context, Handler handler) throws StormServerException {
        Map<Class, Class> converterMap = ConverterCenter.convertMap;
        List<Object> params = new ArrayList<Object>();
        Method method = handler.getMethod();
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            //如果是RequestParam
            if (!Objects.equal(null, parameter.getAnnotation(RequestParam.class))) {
                RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
                //参数名字
                String name = requestParam.name();
                //参数是否必需
                boolean required = requestParam.required();
                //如果@RequestParam未指定name属性，那么直接抛出异常
                if (StringUtils.isEmpty(name)) {
                    logger.error("RequestParam annotation is Illegal, don't have name property");
                    throw new RuntimeException("RequestParam annotation is Illegal, don't have name property");
                }
                //前端未传的参数并且是必需参数
                if (Objects.equal(null, context.params.get(name)) && required) {
                    logger.error("the required param: {} not found", name);
                    throw new RuntimeException(String.format("the required param: %s not found", name));
                }
                Type type = parameter.getParameterizedType();
                //参数的类型
                Class<?> parameterType = parameter.getType();
                //判断接收参数的是不是集合类型
                if (Collections.class.isAssignableFrom(parameterType)) {
                    Type actualType;
                    //如果是的话, 接着判断是不是泛型类型
                    if (type instanceof ParameterizedType) {
                        Type[] types = ((ParameterizedType) type).getActualTypeArguments();
                        if (types.length > 1) {
                            //@RequestParam不支持泛型实际参数为多个的情况，暂不支持
                            throw new StormServerException(String.format("%s is not valid", parameter));
                        }
                        actualType = types[0];
                    } else { //如果不是泛型类型
                        actualType = String.class;
                    }
                }

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
//                        Set
                    }
                }

                //如果参数的类型是String，那么直接可以赋值，这块想不到什么好的处理方式了，先这么写着吧。
                if (parameterType == String.class) {
                    params.add(context.params.get(name));
                    continue;
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

    /**
     * @description 检查参数
     * @param actionMethod
     * @param actionMethodParamList
     */
    private void checkParams(Method actionMethod, List<Object> actionMethodParamList) {
        // 判断 Action 方法参数的个数是否匹配
        Class<?>[] actionMethodParameterTypes = actionMethod.getParameterTypes();
        if (actionMethodParameterTypes.length != actionMethodParamList.size()) {
            String message = String.format("因为参数个数不匹配，所以无法调用 Action 方法！原始参数个数：%d，实际参数个数：%d", actionMethodParameterTypes.length, actionMethodParamList.size());
            throw new RuntimeException(message);
        }
    }

    /**
     * @description String转换成其他支持的类型
     * @param source
     * @param paramType
     * @return
     */
    private Object stringConvert2OtherType(String source, Class<?> paramType) throws StormServerException {
        Map<Class, Class> converterMap = ConverterCenter.convertMap;
        Object result = null;
        converterMap.put(Number.class, StringToNumberConverter.class);
        //参数类型不是String,判断是否是转换器中可以转换的类型
        for (Class type : converterMap.keySet()) {
            if (type.isAssignableFrom(paramType)) {
                try {
                    Class converterClass = converterMap.get(type);
                    Method method = converterClass.getMethod("convert", String.class);
                    Object instance = null;
                    if (type == Number.class) {
                        instance = converterClass.getConstructor(Class.class).newInstance(paramType);
                    } else {
                        instance = converterClass.newInstance();
                    }
                    result = method.invoke(instance, source);
                } catch (Exception e) {
                    logger.error("storm server get converter's convert method failed: {}", e.getMessage());
                    throw new StormServerException(e);
                }
            }
        }
        return result;
    }

    /**
     * @description List<String> 转换成其他集合类型的泛型类参数比如， Set<Integer>
     * @param actualType
     * @param paramType
     * @param params
     * @return
     */
    private Object convert2CollectionType(Type actualType, Class<?> paramType, List<String> params) throws StormServerException {
        if (Objects.equal(null, actualType)) {
            actualType = String.class;
        }
        List<Object> data = new ArrayList<Object>();
        //遍历参数
        for (String param : params) {
            data.add(stringConvert2OtherType(param, (Class<?>) actualType));
        }
        if (paramType == List.class || List.class.isAssignableFrom(paramType)) {
            return data;
        }
        if (paramType == Set.class || Set.class.isAssignableFrom(paramType)) {
            return new HashSet<>(data);
        }
        throw new StormServerException(String.format("%s not supported convert.", paramType));
    }
}
