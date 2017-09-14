package me.stormma.support.utils;

import java.util.Properties;
import java.util.concurrent.Callable;

/**
 * @author stormma
 * @date 2017/09/11
 */
public class EnvironmentUtils {
    private static Properties properties = System.getProperties();

    private static final String DEFAULT_VALUE = "";

    /**
     * get java lib path
     * @return
     */
    public static String getJavaLibPath() {
        return getValue(() -> properties.getProperty("sun.boot.library.path"), DEFAULT_VALUE);
    }

    /**
     * get os name
     * @return
     */
    public static String getOsName() {
        return getValue(() -> properties.getProperty("os.name"), DEFAULT_VALUE);
    }

    /**
     * get author
     * @return
     */
    public static String getAuthor() {
        return getValue(() -> properties.getProperty("user.name"), DEFAULT_VALUE);
    }

    /**
     * get project output dir
     * @return
     */
    public static String getProjectOutputDir() {
        String projectDir = getValue(() -> properties.getProperty("user.dir"), DEFAULT_VALUE);
        String projectName = projectDir.substring(projectDir.lastIndexOf("/") + 1, projectDir.length());
        return projectDir + "/target/" + projectName + ".jar";
    }

    /**
     * get jvm name
     * @return
     */
    public static String getJvmName() {
        return getValue(() -> properties.getProperty("java.vm.name"), DEFAULT_VALUE);
    }

    /**
     * get java version
     * @return
     */
    public static String getJavaVersion() {
        return getValue(() -> properties.getProperty("java.version"), DEFAULT_VALUE);
    }

    /**
     * get value
     * @param call
     * @param defaultValue
     * @return
     */
    private static String getValue(Callable<String> call, String defaultValue) {
        try {
            return call.call();
        } catch (Exception e) {
            //Swallow and continue
        }
        return defaultValue;
    }
}
