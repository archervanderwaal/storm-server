package me.stormma.core.http.response;

/**
 * @author stormma
 * @date 2017/8/14.
 */
public class Response<T> {
    /**
     * error code :错误是1、成功是0
     */
    private Integer code;

    /**
     * 要返回的数据
     */
    private T data;

    /**
     * 本次请求的说明信息
     */
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
