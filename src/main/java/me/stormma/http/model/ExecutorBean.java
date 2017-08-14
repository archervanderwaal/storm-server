package me.stormma.http.model;

import me.stormma.http.enums.RequestMethod;

import java.lang.reflect.Method;

/**
 * @author stormma
 * @date 2017/8/14.
 * @description
 */
public class ExecutorBean {
    /**
     * controller class
     */
    private Object object;

    /**
     * method
     */
    private Method method;

    /**
     * request method
     */
    private RequestMethod requestMethod;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }
}
