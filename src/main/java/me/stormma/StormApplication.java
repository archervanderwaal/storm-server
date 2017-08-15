package me.stormma;

import com.google.common.base.Objects;
import me.stormma.config.ServerConfig;
import me.stormma.exception.ConfigFileNotFoundException;
import me.stormma.http.converter.ConverterCenter;
import me.stormma.http.core.ApiGateway;
import me.stormma.http.core.HttpService;
import me.stormma.http.helper.ApplicationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author stormma
 * @date 2017/8/13.
 * @description storm server 启动类
 */
public class StormApplication {

    private final Logger logger = LoggerFactory.getLogger(StormApplication.class);

    private static StormApplication application;

    private static ApiGateway apiGateway;

    private StormApplication() {
    }

    /**
     * @description smart server 主运行方法
     * @param args
     */
    public static void run(String[] args, String basePackageName) throws Exception {
        String configFilePath = !Objects.equal(null, args) && args.length > 1 ? args[0] : null;
        //init config
        application = new StormApplication(configFilePath);
        apiGateway = ApiGateway.getInstance();
        //start core
        application.startService(basePackageName);
        //设置所有的请求交由总网关处理
        HttpService.getInstance().registerServlet("/", apiGateway);
        ApplicationHelper.logApiMap(application.logger);
        //启动转换器
        ConverterCenter.init();
        HttpService.startJettyServer();
    }

    /**
     * @description init config
     * @param configFilePath
     * @throws Exception
     */
    private StormApplication(String configFilePath) throws Exception {
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
     * @description 启动服务
     */
    private void startService(String basePackageName) throws Exception {
        int port = ServerConfig.PORT;
        if (!(port > 0 && port < (1 << 16) - 1)) {
            logger.error("server port: {} is not valid!", port);
            throw new Exception("server port: " + port + "is not valid!");
        }
        try {
            HttpService.init();
        } catch (Exception e) {
            logger.error("init http core failed: {}", e);
            System.exit(-4);
        }
        ApplicationHelper.initApiMap(basePackageName);
    }
}
