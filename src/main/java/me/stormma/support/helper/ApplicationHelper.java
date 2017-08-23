package me.stormma.support.helper;

import me.stormma.ioc.IocHelper;
import me.stormma.support.converter.helper.ConverterHelper;
import me.stormma.exception.StormServerException;
import me.stormma.http.handler.Handler;
import me.stormma.http.helper.ApiHelper;
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
     * log
     */
    private static final Logger logger = LoggerFactory.getLogger(ApplicationHelper.class);

    /**
     * @description 初始化apiMap, converter以及其他需要初始化的东西
     * @param basePackageName
     */
    public static void init(String basePackageName) {
        //初始化bean
        try {
            beanMap = BeanHelper.initBeanMap(basePackageName);
            IocHelper.initBean(basePackageName);
        } catch (StormServerException e) {
            throw new RuntimeException(e);
        }
        //初始化api
        apiMap = ApiHelper.initApiMap(basePackageName);
        //初始化转换器
        converterMap = ConverterHelper.initConverter(basePackageName);
    }
}
