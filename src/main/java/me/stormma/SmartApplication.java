package me.stormma;

import com.google.common.base.Objects;
import me.stormma.config.ServerConfig;
import me.stormma.exception.ConfigFileNotFoundException;
import me.stormma.http.service.ApiGateway;
import me.stormma.http.service.HttpService;
import me.stormma.http.annotation.Api;
import me.stormma.http.annotation.Controller;
import me.stormma.http.model.ExecutorBean;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * @author stormma
 * @date 2017/8/13.
 * @description smart server 启动类
 */
public class SmartApplication {

    private final Logger logger = LoggerFactory.getLogger(SmartApplication.class);

    private static SmartApplication application;

    private static ApiGateway apiGateway;

    public static Map<String, ExecutorBean> apiMap = new HashMap<String, ExecutorBean>();

    private SmartApplication() {
    }

    /**
     * @description smart server 主运行方法
     * @param args
     */
    public static void run(String[] args, String basePackageName) throws Exception {
        String configFilePath = !Objects.equal(null, args) && args.length > 1 ? args[0] : null;
        //init config
        application = new SmartApplication(configFilePath);
        apiGateway = ApiGateway.getInstance();
        //start service
        application.startService(basePackageName);
        //设置所有的请求交由总网关处理
        HttpService.getInstance().registerServlet("/", apiGateway);
        application.listApi();
        HttpService.getInstance().startJettyServer();
    }

    /**
     * @description 打印api
     */
    private void listApi() {
        for (String url : apiMap.keySet()) {
            ExecutorBean executorBean = apiMap.get(url);
            application.logger.info("api: {}=>{}=>{}", url, executorBean.getRequestMethod()
                    , executorBean.getClass().getName() + "." + executorBean.getMethod().getName());
        }
    }

    /**
     * @description init config
     * @param configFilePath
     * @throws Exception
     */
    private SmartApplication(String configFilePath) throws Exception {
        try {
            initConfig(configFilePath);
        } catch (ConfigFileNotFoundException e) {
            logger.error("start application filed: {}", e.getMessage());
            e.printStackTrace();
            System.exit(-2);
        }
    }

    /**
     * @description init config
     * @param configFilePath
     */
    private void initConfig(String configFilePath) throws ConfigFileNotFoundException {
        if (Objects.equal(null, configFilePath) || Objects.equal("", configFilePath)) {
            ServerConfig.init();
        } else {
            ServerConfig.init(configFilePath);
        }
    }

    /**
     * @description 初始化server服务
     */
    private void startService(String basePackageName) throws Exception {
        int port = ServerConfig.PORT;
        //判断端口的有效性
        if (!(port > 0 && port < (1 << 16) - 1)) {
            logger.error("server port: {} is not valid!", port);
            throw new Exception("server port: " + port + "is not valid!");
        }
        try {
            //初始化http 服务
            HttpService.init();
        } catch (Exception e) {
            logger.error("init http service failed: {}", e);
            System.exit(-4);
        }
        initApiMap(basePackageName);
    }

    /**
     * @description 初始化apiMap
     * @param basePackageName
     */
    private void initApiMap(String basePackageName) {
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
                    ExecutorBean executorBean = new ExecutorBean();
                    try {
                        executorBean.setObject(apiClass.newInstance());
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    executorBean.setMethod(method);
                    executorBean.setRequestMethod(api.method());
                    String urlStart = controller.value().endsWith("/") ? controller.value()
                            .substring(0, controller.value().length() - 1) : controller.value();
                    String urlEnd = api.url().startsWith("/") ? api.url() : "/" + api.url();
                    urlStart = urlStart.startsWith("/") ? urlStart : "/" + urlStart;
                    //url as ==> /xxx/xxx/xx
                    String finalUrl = urlStart + (urlEnd.endsWith("/") ? urlEnd
                                                    .substring(0, urlEnd.length() - 1) : urlEnd);
                    apiMap.put(finalUrl, executorBean);
                }
            }
        }
    }
}
