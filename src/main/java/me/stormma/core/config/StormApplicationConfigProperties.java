package me.stormma.core.config;

/**
 * @author stormma
 * @date 2017/09/12
 */
public class StormApplicationConfigProperties {
    /**
     * PORT
     */
    public static final String PORT = "storm.server.port";

    /**
     * module
     */
    public static final String MODULE = "storm.server.module";

    /**
     * ansi output enabled
     */
    public static final String ANSI_OUTPUT_ENABLED = "storm.ansi.output.enabled";

    /**
     * 收件人
     */
    public static final String EMAIL_TO_ADDRESS = "storm.mail.email_to_address";

    /**
     * default config file path ==> path + file name, default path is 'classpath/storm.properties'
     */
    public static final String DEFAULT_CONFIG_FILE_NAME = "storm.properties";

    /**
     * jetty thread config
     */
    public static final boolean CUSTOMIZE_THREAD_POOL = false;

    /**
     * max thread count
     */
    public static final int MAX_THREAD_COUNT = Integer.MAX_VALUE;

    /**
     * min thread count
     */
    public static final int MIN_THREAD_COUNT = 1024;

    /**
     * thread time out
     */
    public static final int THREAD_TIMEOUT = 100 * 1000;

    /**
     * io time out
     */
    public static final int IO_TIMEOUT = 30000;
}
