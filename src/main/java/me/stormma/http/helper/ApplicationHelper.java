package me.stormma.http.helper;

import com.google.common.base.Objects;
import me.stormma.http.annotation.Api;
import me.stormma.http.annotation.Controller;
import me.stormma.http.handler.Handler;
import org.reflections.Reflections;
import org.slf4j.Logger;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author stormma
 * @date 2017/8/15.
 * @description application helper
 */
public class ApplicationHelper {

    /**
     * api map
     */
    private static final Map<String, Handler> apiMap = new LinkedHashMap<String, Handler>();

    /**
     * @param basePackageName
     * @description init api map
     */
    public static void initApiMap(String basePackageName) {
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
    }

    /**
     * @return
     * @description 获得所有的api映射
     */
    public static Map<String, Handler> listApiMap() {
        return apiMap;
    }

    /**
     * @description 打印api日志
     * @param logger
     */
    public static void logApiMap(Logger logger) {
        for (String url : apiMap.keySet()) {
            Handler executorBean = apiMap.get(url);
            logger.info("api: {}=>{}=>{}", url, executorBean.getRequestMethod()
                    , executorBean.getClass().getName() + "." + executorBean.getMethod().getName());
        }
    }
}
