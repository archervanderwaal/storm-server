package me.stormma.support.utils;

import java.util.Properties;

/**
 * @author stormma
 * @date 2017/09/11
 */
public class EnvironmentUtils {
    private static Properties properties = System.getProperties();

    /**
     * get host name
     * @return
     */
    public static String getHostName() {
        return properties.getProperty(null);
    }

    /**
     * get os name
     * @return
     */
    public static String getOsName() {
        return properties.getProperty("os.name");
    }

    /**
     * get author
     * @return
     */
    public static String getAuthor() {
        return properties.getProperty("user.name");
    }

    /**
     * get project output dir
     * @return
     */
    public static String getProjectOutputDir() {
        String projectDir = properties.getProperty("user.dir");
        String projectName = projectDir.substring(projectDir.lastIndexOf("/") + 1, projectDir.length());
        return projectDir + "/target/" + projectName + ".jar";
    }

    /**
     * get jvm name
     * @return
     */
    public static String getJvmName() {
        return properties.getProperty("java.vm.name");
    }

    /**
     * get java version
     * @return
     */
    public static String getJavaVersion() {
        return properties.getProperty("java.version");
    }
}
