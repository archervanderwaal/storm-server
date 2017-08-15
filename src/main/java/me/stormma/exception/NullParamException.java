package me.stormma.exception;

/**
 * @author stormma
 * @date 2017/8/15.
 */
public class NullParamException extends Exception {
    public NullParamException() {
    }

    public NullParamException(String message) {
        super(message);
    }

    public NullParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullParamException(Throwable cause) {
        super(cause);
    }

    public NullParamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
