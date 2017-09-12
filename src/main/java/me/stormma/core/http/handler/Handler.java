package me.stormma.core.http.handler;

import me.stormma.core.http.enums.RequestMethod;

import java.lang.reflect.Method;

/**
 * @author stormma
 * @date 2017/8/14.
 * @description
 */
public class Handler {
    /**
     * controller class
     */
    private Class<?> controllerClass;

    /**
     * method
     */
    private Method method;

    /**
     * request method
     */
    private RequestMethod requestMethod;

    public Handler() {
    }

    public Handler(Class<?> controllerClass, Method method) {
        this.controllerClass = controllerClass;
        this.method = method;
    }

    public Handler(Class<?> controllerClass, Method method, RequestMethod requestMethod) {
        this.controllerClass = controllerClass;
        this.method = method;
        this.requestMethod = requestMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
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
