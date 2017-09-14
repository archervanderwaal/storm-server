package me.stormma.core.config;

/**
 * @author stormma
 * @date 2017/09/12
 */
public class StormApplicationConfig {

    /**
     * server port
     */
    public static Integer PORT;

    /**
     * module
     */
    public static String MODULE;

    /**
     * ansi output enabled.
     */
    public static Boolean ANSI_OUTPUT_ENABLED = Boolean.FALSE;

    /**
     * email to address
     */
    public static String EMAIL_TO_ADDRESS;

    /**
     * email is enabled.
     */
    public static Boolean EMAIL_IS_ENABLED = Boolean.FALSE;

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
}
