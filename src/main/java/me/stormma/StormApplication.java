package me.stormma;

import com.google.common.base.Objects;
import me.stormma.annotation.Application;
import me.stormma.annotation.ComponentScan;
import me.stormma.config.ServerConfig;
import me.stormma.exception.ConfigFileNotFoundException;
import me.stormma.fault.InitializationError;
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
            throw new InitializationError(String.format("reflect %s failed, message: %s", className, e.getMessage()));
        }
        ComponentScan componentScan = clazz.getAnnotation(ComponentScan.class);
        Application application = clazz.getAnnotation(Application.class);
        if (Objects.equal(null, componentScan) || Objects.equal(null, application)) {
            throw new InitializationError(String.format("%s no @Application or @ComponentScan annotation", className));
        }
        String basePackageName;
        if (!Objects.equal(null, componentScan)) {
            basePackageName = componentScan.value().equals("") ?
                    className.substring(0, className.lastIndexOf(".")) : componentScan.value();
        } else {
            basePackageName = application.value().getName()
                    .substring(0, application.value().getName().lastIndexOf("."));
        }
        String configFilePath = !Objects.equal(null, args) && args.length > 1 ? args[0] : null;
        instance = new StormApplication(configFilePath);
        apiGateway = ApiGateway.getInstance();
        instance.startService(basePackageName);
        //初始化
        ApplicationHelper.init(basePackageName);
        try {
            HttpService.getInstance().registerServlet("/", apiGateway);
            HttpService.startJettyServer();
        } catch (Exception e) {
            throw new InitializationError("start jetty server failed", e);
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
            throw new InitializationError(String
                    .format("config file not found, please check out it. message: %s", e.getMessage()));
        }
    }

    /**
     * @param basePackageName
     * @description 启动服务
     */
    private void startService(String basePackageName) {
        int port = ServerConfig.PORT;
        if (!(port > 0 && port < (1 << 16) - 1)) {
            throw new InitializationError(String.format("server port: %s is not valid!", port));
        }
        try {
            HttpService.init();
        } catch (Exception e) {
            throw new InitializationError(String.format("init http core service failed: %s", e));
        }
    }
}
