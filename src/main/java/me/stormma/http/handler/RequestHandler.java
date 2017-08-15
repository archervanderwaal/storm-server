package me.stormma.http.handler;

import com.google.common.base.Objects;
import me.stormma.http.model.HttpContext;
import me.stormma.http.annotation.JsonParam;
import me.stormma.http.annotation.RequestParam;
import me.stormma.http.converter.Converter;
import me.stormma.http.response.Response;
import me.stormma.http.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author stormma
 * @date 2017/8/14.
 * @description 请求处理
 */
public class RequestHandler {

    private static RequestHandler instance;

    private final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static RequestHandler getInstance() {
        if (Objects.equal(null, instance)) {
            synchronized (RequestHandler.class) {
                if (Objects.equal(null, instance)) {
                    instance = new RequestHandler();
                }
            }
        }
        return instance;
    }

    /**
     * 请求处理
     *
     * @param context
     * @param bean
     */
    public void handleRequest(HttpContext context, Handler bean) throws ParseException, InvocationTargetException, IllegalAccessException, InstantiationException {
        //要执行的方法
        Method method = bean.getMethod();
        Class[] paramTypes = method.getParameterTypes();
        List<Object> args = new ArrayList<Object>();
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            if (!Objects.equal(null, parameter.getAnnotation(JsonParam.class))) {
                //args.add(JsonUtil.byteArrayConvert2JavaBean(context.requestBody, parameter.getType()));
            }
            if (!Objects.equal(null, parameter.getAnnotation(RequestParam.class))) {
                RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
                String name = requestParam.name();
                if (Objects.equal(null, name)) {
                    logger.error("{} request param is not valid, because RequestParam() annotation no name"
                            , bean.getMethod());
                    throw new RuntimeException(String
                            .format("%s request param is not valid, because RequestParam() annotation no name"
                                    , bean.getMethod()));
                }
                String value = (String) context.params.get(name);
                Class clazz = parameter.getType();
                Converter.convert(value, parameter.getType());
//                args.add();
//                Convert.
            }
        }
        Object o = method.invoke(bean.getControllerClass().newInstance(), args);
    }
    private <T> Response<T> invoke(HttpContext context, Handler bean) {
        return null;
    }
}
