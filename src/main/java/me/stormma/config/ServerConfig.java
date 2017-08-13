package me.stormma.config;

import me.stormma.exception.ConfigFileNotFoundException;

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
     * REQUEST_ID
     */
    public static String REQUEST_ID;

    /**
     * PORT
     */
    public static String PORT;

    /**
     * properties
     */
    private static Properties properties;

    /**
     * config file path
     */
    private static String configFilePath;

    /**
     * default config file path ==> path + file name, default path is 'classpath/smart.properties'
     */
    private static String DEFAULT_CONFIG_FILE_NAME = "smart.properties";

    private static String getDefaultConfigFileName() {
        return DEFAULT_CONFIG_FILE_NAME;
    }


    /**
     * @description init config
     * @param path
     * @throws ConfigFileNotFoundException
     */
    public static void init(String path) throws ConfigFileNotFoundException {
        configFilePath = path;
        InputStream configInputStream = ServerConfig.class.getClassLoader().getResourceAsStream(path);
        properties = new Properties();
        try {
            properties.load(configInputStream);
        } catch (Exception e) {
            throw new ConfigFileNotFoundException("the config file named '" + path + "' not found");
        }
        //init config filed
        SERVER_ID = properties.getProperty(ConfigProperties.SERVER_ID);
        PORT = properties.getProperty(ConfigProperties.PORT);
        REQUEST_ID = properties.getProperty(ConfigProperties.REQUEST_ID);
    }

    /**
     * @description init config
     * @throws ConfigFileNotFoundException
     */
    public static void init() throws ConfigFileNotFoundException {
        init(DEFAULT_CONFIG_FILE_NAME);
    }
}
