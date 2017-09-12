package me.stormma.core.http.helper;

import com.google.common.base.Objects;
import me.stormma.core.http.annotation.Api;
import me.stormma.core.http.handler.Handler;
import me.stormma.ioc.annotation.Controller;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @description api helper，注册api的帮助类
 * @author stormma
 * @date 2017/8/20
 */
public class ApiHelper {

    /**
     * logback
     */
    private static final Logger logger = LoggerFactory.getLogger(ApiHelper.class);

    /**
     * @param basePackageName
     * @description init api map
     */
    public static Map<String, Handler> initApiMap(String basePackageName) {
        Map<String, Handler> apiMap = new LinkedHashMap<>();
        Reflections reflections = new Reflections(basePackageName);
        Set<Class<?>> apiClasses = reflections.getTypesAnnotatedWith(Controller.class);
        //遍历class
        for (Class apiClass : apiClasses) {
            Controller controller = (Controller) apiClass.getAnnotation(Controller.class);
            Method[] methods = apiClass.getDeclaredMethods();
            //遍历method
            for (Method method : methods) {
                Api api = method.getAnnotation(Api.class);
                if (!Objects.equal(null, api)) {
                    Handler handler = new Handler();
                    handler.setControllerClass(apiClass);
                    handler.setMethod(method);
                    handler.setRequestMethod(api.method());
                    String urlStart = controller.value().endsWith("/") ? controller.value()
                            .substring(0, controller.value().length() - 1) : controller.value();
                    String urlEnd = api.url().startsWith("/") ? api.url() : "/" + api.url();
                    urlStart = urlStart.startsWith("/") ? urlStart : "/" + urlStart;
                    String finalUrl = urlStart + (urlEnd.endsWith("/") ? urlEnd
                            .substring(0, urlEnd.length() - 1) : urlEnd);
                    apiMap.put(finalUrl, handler);
                }
            }
        }
        //logback api map
        logApiMap(apiMap);
        return apiMap;
    }

    /**
     * @description 打印api日志
     * @param apiMap
     */
    private static void logApiMap(Map<String, Handler> apiMap) {
        for (String url : apiMap.keySet()) {
            Handler executorBean = apiMap.get(url);
            String methodName = executorBean.getMethod().getName();
            logger.info("Mapped \"url=[{}], methods=[{}] on {}\"", url, executorBean.getRequestMethod(),
                        executorBean.getControllerClass() + "." + methodName.substring(methodName
                                .lastIndexOf(".") + 1, methodName.length()));
        }
    }
}
