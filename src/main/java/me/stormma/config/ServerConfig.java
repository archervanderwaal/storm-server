package me.stormma.config;

import com.google.common.base.Objects;
import me.stormma.constant.StormApplicationConstant;
import me.stormma.exception.ConfigFileNotFoundException;
import me.stormma.mail.MailService;
import me.stormma.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author stormma
 * @date 2017/8/12.
 * @description SERVER CONFIG
 */
public class ServerConfig {

    /**
     * SERVER_ID
     */
    public static String SERVER_ID;

    /**
     * module name
     */
    public static String MODULE_NAME;

    /**
     * PORT
     */
    public static int PORT;

    /**
     * jetty thread config
     */
    public static boolean CUSTOMIZE_THREAD_POOL = false;

    /**
     * max thread count
     */
    public static int MAX_THREAD_COUNT = Integer.MAX_VALUE;

    /**
     * min thread count
     */
    public static int MIN_THREAD_COUNT = 1024;

    /**
     * thread time out
     */
    public static int THREAD_TIMEOUT = 100 * 1000;

    /**
     * io time out
     */
    public static int IO_TIMEOUT = 30000;

    /**
     * default config file path ==> path + file name, default path is 'classpath/storm.properties'
     */
    private static String DEFAULT_CONFIG_FILE_NAME = "storm.properties";

    /**
     * log
     */
    private static final Logger logger = LoggerFactory.getLogger(ServerConfig.class);


    /**
     * @param path
     * @throws ConfigFileNotFoundException
     * @description init config
     */
    public static void init(String path) throws ConfigFileNotFoundException {
        InputStream configInputStream = ServerConfig.class.getClassLoader().getResourceAsStream(path);
        Properties properties = new Properties();
        try {
            properties.load(configInputStream);
        } catch (Exception e) {
            String eMsg = "the config file named '" + path + "' not found";
            throw new ConfigFileNotFoundException(eMsg, e);
        }
        //init config filed
        SERVER_ID = properties.getProperty(ConfigProperties.SERVER_ID);
        PORT = Objects.equal(null, properties.getProperty(ConfigProperties.PORT)) ?
                StormApplicationConstant.DEFAULT_SERVER_PORT : Integer
                .parseInt(properties.getProperty(ConfigProperties.PORT));
        MODULE_NAME = properties.getProperty(ConfigProperties.MODULE_NAME);
        MailConfig.EMAIL_TO_ADDRESS = properties.getProperty(ConfigProperties.EMAIL_TO_ADDRESS);
        if (!StringUtils.isEmpty(MailConfig.EMAIL_TO_ADDRESS)) {
            MailConfig.isEnabled = true;
        }
    }

    /**
     * @throws ConfigFileNotFoundException
     * @description init config
     */
    public static void init() throws ConfigFileNotFoundException {
        init(DEFAULT_CONFIG_FILE_NAME);
    }
}
