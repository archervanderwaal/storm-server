package me.stormma.http.service;

import me.stormma.http.util.HttpUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.HashMap;

/**
 * @author stormma
 * @date 2017/8/13.
 * @description 封装req, resp
 */
public class HttpContext {
    /**
     * module_name
     */
    public String moduleName;

    /**
     * request
     */
    public HttpServletRequest request;

    /**
     * response
     */
    public HttpServletResponse response;

    /**
     * user_agent
     */
    public String userAgent;

    /**
     * cookie
     */
    public Cookie[] cookies;

    /**
     * ip address
     */
    public String ip;

    /**
     * mac address
     */
    public String mac;

    /**
     * request params
     */
    public Map<String, Object> params;

    /**
     * request start time
     */
    public long requestStartTime;

    /**
     * request end time
     */
    public long requestEndTime;

    /**
     * request id
     */
    public String requestId;

    /**
     * requestPath
     */
    public String requestPath;

    /**
     * request body
     */
    public byte[] requestBody;

    public HttpContext(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.ip = HttpUtil.getRealIp(request);
        this.requestStartTime = System.currentTimeMillis();
        this.params = new HashMap<String, Object>();
    }

    public HttpContext(HttpServletRequest request, HttpServletResponse response, String moduleName) {
        this.request = request;
        this.response = response;
        this.moduleName = moduleName;
        this.ip = HttpUtil.getRealIp(request);
        this.requestStartTime = System.currentTimeMillis();
        this.params = new HashMap<String, Object>();
    }
}
