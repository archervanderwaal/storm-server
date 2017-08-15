package me.stormma.exception;

/**
 * @author stormma
 * @date 2017/8/14.
 */
public class StormServerException extends Exception {

    public StormServerException() {
    }

    public StormServerException(String message) {
        super(message);
    }

    public StormServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public StormServerException(Throwable cause) {
        super(cause);
    }

    public StormServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
