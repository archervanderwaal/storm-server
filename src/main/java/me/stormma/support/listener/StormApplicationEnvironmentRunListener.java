package me.stormma.support.listener;

import me.stormma.constant.StormApplicationConstant;
import me.stormma.core.config.Environment;
import me.stormma.core.config.StormApplicationConfig;
import me.stormma.core.config.StormApplicationConfigProperties;
import me.stormma.fault.InitializationError;
import me.stormma.support.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * @Motto 总有人成功的，那个人为什么不能是我
 * @author stormma
 * @date 2017/09/12
 */
public class StormApplicationEnvironmentRunListener extends AbstractStormApplicationRunListener {

    /**
     * config file path
     */
    private String configFilePath;

    private static final Logger logger = LoggerFactory.getLogger(StormApplicationEnvironmentRunListener.class);

    @Override
    public void environmentPrepared(Environment environment) {
        this.configFilePath = environment.getStormApplicationConfigFilePath();
        initStormApplicationEnvironment(this.configFilePath);
    }

    /**
     * 初始化配置
     * @param path
     */
    private void initStormApplicationEnvironment(String path) {
        InputStream configInputStream = StormApplicationConfig.class.getClassLoader().getResourceAsStream(path);
        Properties properties = new Properties();
        try {
            properties.load(configInputStream);
        } catch (Exception e) {
            String eMsg = "the config file named '" + path + "' not found";
            throw new InitializationError(eMsg);
        }
        me.stormma.core.config.StormApplicationConfig.PORT = null == properties.getProperty(StormApplicationConfigProperties.PORT) ?
                StormApplicationConstant.DEFAULT_SERVER_PORT : Integer
                .parseInt(properties.getProperty(StormApplicationConfigProperties.PORT));
        if (!(me.stormma.core.config.StormApplicationConfig.PORT > 0 && me.stormma.core.config.StormApplicationConfig.PORT < (1 << 16) - 1)) {
            throw new InitializationError(String.format("server port: %s is not valid!", me.stormma.core.config.StormApplicationConfig.PORT));
        }
        me.stormma.core.config.StormApplicationConfig.MODULE = properties.getProperty(StormApplicationConfigProperties.MODULE);
        me.stormma.core.config.StormApplicationConfig.EMAIL_TO_ADDRESS = properties.getProperty(StormApplicationConfigProperties.EMAIL_TO_ADDRESS);
        me.stormma.core.config.StormApplicationConfig.ANSI_OUTPUT_ENABLED = new Boolean(properties.getProperty(StormApplicationConfigProperties.ANSI_OUTPUT_ENABLED));
        if (!StringUtils.isEmpty(me.stormma.core.config.StormApplicationConfig.EMAIL_TO_ADDRESS)) {
            me.stormma.core.config.StormApplicationConfig.EMAIL_IS_ENABLED = Boolean.TRUE;
        }
    }
}
