package me.stormma.exception;

/**
 * @description
 * @author stormma
 * @date 2017/8/20
 */
public class NoSuchBeanException extends StormServerException {

    public NoSuchBeanException() {
    }

    public NoSuchBeanException(String message) {
        super(message);
    }

    public NoSuchBeanException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchBeanException(Throwable cause) {
        super(cause);
    }

    public NoSuchBeanException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
