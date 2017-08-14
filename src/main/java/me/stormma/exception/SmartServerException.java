package me.stormma.exception;

/**
 * @author stormma
 * @date 2017/8/14.
 */
public class SmartServerException extends Exception {

    public SmartServerException() {
    }

    public SmartServerException(String message) {
        super(message);
    }

    public SmartServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public SmartServerException(Throwable cause) {
        super(cause);
    }

    public SmartServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
