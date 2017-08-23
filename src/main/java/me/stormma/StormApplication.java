package me.stormma;

import com.google.common.base.Objects;
import me.stormma.annotation.Application;
import me.stormma.annotation.ComponentScan;
import me.stormma.config.ServerConfig;
import me.stormma.exception.ConfigFileNotFoundException;
import me.stormma.exception.StormServerException;
import me.stormma.support.helper.ApplicationHelper;
import me.stormma.http.core.ApiGateway;
import me.stormma.http.core.HttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author stormma
 * @date 2017/8/13.
 * @description storm-server主类
 */
public class StormApplication {

    private static final Logger logger = LoggerFactory.getLogger(StormApplication.class);

    private static StormApplication instance;

    private static ApiGateway apiGateway;

    private StormApplication() {
    }

    /**
     * @param args
     * @description
     */
    public static void run(String[] args) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement a = (StackTraceElement) stackTrace[2];
        String className = a.getClassName();
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.error("reflect {} failed, message: {}", className, e.getMessage());
            return;
        }
        ComponentScan componentScan = clazz.getAnnotation(ComponentScan.class);
        Application application = clazz.getAnnotation(Application.class);
        if (Objects.equal(null, componentScan) && Objects.equal(null, application)) {
            throw new RuntimeException(String.format("%s no @Application or @ComponentScan annotation", className));
        }
        String basePackageName;
        if (!Objects.equal(null, componentScan)) {
            basePackageName = Objects.equal(null, componentScan.value()) ?
                    className.substring(0, className.lastIndexOf(".")) : componentScan.value();
        }
        basePackageName = application.value().getName().substring(0, application.value().getName().lastIndexOf("."));
        String configFilePath = !Objects.equal(null, args) && args.length > 1 ? args[0] : null;
        instance = new StormApplication(configFilePath);
        apiGateway = ApiGateway.getInstance();
        instance.startService(basePackageName);
        //初始化
        ApplicationHelper.init(basePackageName);
        try {
            HttpService.getInstance().registerServlet("/", apiGateway);
        } catch (StormServerException e) {
            logger.error("register servlet failed, message: {}", e.getMessage());
        }
        try {
            HttpService.startJettyServer();
        } catch (Exception e) {
            logger.error("start jetty server failed, message: {}", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * @param configFilePath
     * @throws Exception
     */
    private StormApplication(String configFilePath) {
        try {
            if (Objects.equal(null, configFilePath) || Objects.equal("", configFilePath)) {
                ServerConfig.init();
            } else {
                ServerConfig.init(configFilePath);
            }
        } catch (ConfigFileNotFoundException e) {
            logger.error("config file not found, please check out it. message: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * @description 启动服务
     * @param basePackageName
     */
    private void startService(String basePackageName) {
        int port = ServerConfig.PORT;
        if (!(port > 0 && port < (1 << 16) - 1)) {
            logger.error("server port: {} is not valid!", port);
            System.exit(-1);
        }
        try {
            HttpService.init();
        } catch (Exception e) {
            logger.error("init http core service failed: {}", e);
            throw new RuntimeException(e);
        }
    }
}
