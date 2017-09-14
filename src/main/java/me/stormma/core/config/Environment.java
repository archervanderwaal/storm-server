package me.stormma.core.config;

import org.slf4j.Logger;

/**
 * @author stormma
 * @date 2017/09/12
 */
public class Environment {

    /**
     * storm application config file path
     */
    private String stormApplicationConfigFilePath;

    /**
     * storm application component scan package name
     */
    private String stormApplicationComponentScanPackage;

    /**
     * storm application source class
     */
    private Class stormApplicationSourceClass;

    /**
     * logger
     */
    private Logger logger;

    public Environment() {
    }

    public Environment(String stormApplicationConfigFilePath, String stormApplicationComponentScanPackage, Class stormApplicationSourceClass) {
        this.stormApplicationConfigFilePath = stormApplicationConfigFilePath;
        this.stormApplicationComponentScanPackage = stormApplicationComponentScanPackage;
        this.stormApplicationSourceClass = stormApplicationSourceClass;
    }

    public String getStormApplicationConfigFilePath() {
        return stormApplicationConfigFilePath;
    }

    public void setStormApplicationConfigFilePath(String stormApplicationConfigFilePath) {
        this.stormApplicationConfigFilePath = stormApplicationConfigFilePath;
    }

    public String getStormApplicationComponentScanPackage() {
        return stormApplicationComponentScanPackage;
    }

    public void setStormApplicationComponentScanPackage(String stormApplicationComponentScanPackage) {
        this.stormApplicationComponentScanPackage = stormApplicationComponentScanPackage;
    }

    public Class getStormApplicationSourceClass() {
        return stormApplicationSourceClass;
    }

    public void setStormApplicationSourceClass(Class stormApplicationSourceClass) {
        this.stormApplicationSourceClass = stormApplicationSourceClass;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
