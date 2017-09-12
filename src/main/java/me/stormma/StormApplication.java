package me.stormma;

import com.google.common.base.Objects;
import me.stormma.annotation.Application;
import me.stormma.annotation.ComponentScan;
import me.stormma.ansi.AnsiOutput;
import me.stormma.config.ServerConfig;
import me.stormma.exception.ConfigFileNotFoundException;
import me.stormma.fault.InitializationError;
import me.stormma.support.banner.Banner;
import me.stormma.support.banner.StormApplicationBanner;
import me.stormma.support.helper.ApplicationHelper;
import me.stormma.core.http.core.ApiGateway;
import me.stormma.core.http.core.HttpService;
import me.stormma.support.utils.ClassUtils;
import me.stormma.support.utils.EnvironmentUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;

/**
 * @author stormma
 * @date 2017/8/13.
 * @description storm-server主类
 */
public class StormApplication {

    private static final Logger logger = LoggerFactory.getLogger(StormApplication.class);

    private static StormApplication instance;

    private static ApiGateway apiGateway;

    private static Banner banner = new StormApplicationBanner();

    private StormApplication() {
    }

    /**
     * @param args
     * @description
     */
    public static void run(String[] args) {
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
        Class<?> clazz = ClassUtils.getPreCallClass(3);
        String className = clazz.getName();
        banner.printBanner(null, new PrintStream(System.out));
        logger.info(String.format("Starting %s on %s (%s) by %s", className.substring(className
                .lastIndexOf(".") + 1, className.length()), EnvironmentUtils.getOsName(), EnvironmentUtils.getProjectOutputDir(),
                EnvironmentUtils.getAuthor()));
        ComponentScan componentScan = clazz.getAnnotation(ComponentScan.class);
        Application application = clazz.getAnnotation(Application.class);
        if (componentScan == null && application == null) {
            throw new InitializationError(String.format("%s no Application and ComponentScan annotation", className));
        }
        String basePackageName;
        if (componentScan != null) {
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
            logger.info("storm-server start success. listen on " + ServerConfig.PORT);
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
