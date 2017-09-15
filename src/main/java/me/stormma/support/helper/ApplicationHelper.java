package me.stormma.support.helper;

import me.stormma.ioc.IocHelper;
import me.stormma.support.converter.helper.ConverterHelper;
import me.stormma.exception.StormServerException;
import me.stormma.core.http.handler.Handler;
import me.stormma.core.http.helper.ApiHelper;
import me.stormma.ioc.helper.BeanHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author stormma
 * @date 2017/8/15.
 * @description application helper
 */
public class ApplicationHelper {

    /**
     * api map
     */
    public static Map<String, Handler> apiMap = new LinkedHashMap<>();

    /**
     * bean map
     */
    public static Map<Class<?>, Object> beanMap = new LinkedHashMap<>();

    /**
     * converter map
     */
    public static Map<Class<?>, Class<?>> converterMap = new LinkedHashMap<>();

    /**
     * logback
     */
    private static final Logger logger = LoggerFactory.getLogger(ApplicationHelper.class);

    public static void initStormApplicationIoc(String basePackageName) {
        //初始化bean
        try {
            beanMap = BeanHelper.initBeanMap(basePackageName);
        } catch (StormServerException e) {
            logger.error("init bean failed, message {}", e.getMessage());
            e.printStackTrace();
        }
        IocHelper.initBean(basePackageName);
    }

    public static void initStormApplicationApiMap(String basePackageName) throws StormServerException {
        apiMap = ApiHelper.initApiMap(basePackageName);
    }

    public static void initStormApplicationConverterMap(String basePackageName) {
        converterMap = ConverterHelper.initConverter(basePackageName);
    }
}
