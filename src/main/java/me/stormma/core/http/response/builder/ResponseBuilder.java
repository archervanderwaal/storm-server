package me.stormma.core.http.response.builder;

import me.stormma.core.http.response.Response;

/**
 * @author stormma
 * @date 2017/8/14.
 * @description 响应创建类
 */
public class ResponseBuilder {

    /**
     * @description 成功响应数据
     * @param t
     * @param <T>
     * @return
     */
    public static <T> Response<T> success(T t) {
        Response<T> response = new Response<>();
        response.setCode(0);
        response.setMsg("OK");
        response.setData(t);
        response.setStatus(0);
        return response;
    }

    /**
     * 请求理论上成功，数据返回与预期不一样，设置一个status
     * @param t
     * @param status
     * @param <T>
     * @return
     */
    public static <T> Response<T> success(T t, int status) {
        Response<T> response = success();
        response.setStatus(status);
        return response;
    }

    /**
     * @description 成功响应，但是无数据返回
     * @param <T>
     * @return
     */
    public static <T> Response<T> success() {
        return success(null);
    }

    /**
     * @description 响应失败,且无错误信息
     * @param <T>
     * @return
     */
    public static <T> Response<T> fail() {
        Response<T> response = new Response<>();
        response.setCode(1);
        response.setMsg("fail");
        response.setData(null);
        return response;
    }

    /**
     * @description 响应失败，且有错误信息
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Response<T> fail(String msg) {
        Response<T> response = fail();
        response.setMsg(msg);
        response.setStatus(-1);
        return response;
    }

    /**
     * 带status的响应错误封装
     * @param msg
     * @param status
     * @param <T>
     * @return
     */
    public static <T> Response<T> fail(String msg, int status) {
        Response<T> response = fail(msg);
        response.setStatus(status);
        return response;
    }
}
