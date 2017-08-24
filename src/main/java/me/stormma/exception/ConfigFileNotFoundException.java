package me.stormma.exception;

/**
 * @author stormma
 * @date 2017/8/12.
 * @description 找不到配置文件异常
 */
public class ConfigFileNotFoundException extends StormServerException {

    public ConfigFileNotFoundException() {
    }

    public ConfigFileNotFoundException(String message) {
        super(message);
    }

    public ConfigFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigFileNotFoundException(Throwable cause) {
        super(cause);
    }

    public ConfigFileNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
