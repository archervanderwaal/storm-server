package me.stormma.http.response.builder;

import me.stormma.http.response.Response;

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
        Response<T> response = new Response<T>();
        response.setCode(0);
        response.setMsg("success");
        response.setData(t);
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
        Response<T> response = new Response<T>();
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
        return response;
    }
}
