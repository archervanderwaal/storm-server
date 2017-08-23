package me.stormma.http.util;

import me.stormma.constant.StormApplicationConstant;
import me.stormma.http.model.HttpContext;
import me.stormma.http.response.Response;
import me.stormma.http.response.builder.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

/**
 * @description web相关工具类
 * @author stormma
 * @date 2017/8/17
 */
public class WebUtils {

    public static final Logger logger = LoggerFactory.getLogger(WebUtils.class);
    /**
     * @description 发送成功的响应，response响应主体内容
     * @param context
     * @param response
     */
    public static void sendSuccessResponse(HttpContext context, Response response) {
        context.response.setStatus(StormApplicationConstant.OK_HTTP_STATUS);
        context.response.setHeader(StormApplicationConstant.CONTENT_TYPE, StormApplicationConstant.JSON_TYPE);
        ServletOutputStream os = null;
        try {
            os = context.response.getOutputStream();
            os.write(JsonUtil.objConvert2JsonStr(response)
                    .getBytes(StormApplicationConstant.UTF_8));
            os.flush();
            os.close();
        } catch (IOException e) {
            logger.error("send response failed, message: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * @description 发送404响应
     * @param context
     */
    public static void send404Response(HttpContext context) {
        Response<String> response = ResponseBuilder.fail(StormApplicationConstant.NOT_FOUND_HTTP_STATUS_DESC);
        context.response.setHeader(StormApplicationConstant.CONTENT_TYPE, StormApplicationConstant.JSON_TYPE);
        context.response.setStatus(StormApplicationConstant.NOT_FOUND_HTTP_STATUS);
        ServletOutputStream os = null;
        try {
            os = context.response.getOutputStream();
            os.write(JsonUtil.objConvert2JsonStr(response)
                    .getBytes(StormApplicationConstant.UTF_8));
            os.flush();
            os.close();
        } catch (IOException e) {
            logger.error("send response failed, message: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * @description 发送服务器错误响应
     * @param context
     */
    public static void send500Response(HttpContext context) {
        Response<String> response = ResponseBuilder.fail(StormApplicationConstant.SERVER_EXCEPTION_HTTP_STATUS_DESC);
        context.response.setHeader(StormApplicationConstant.CONTENT_TYPE, StormApplicationConstant.JSON_TYPE);
        context.response.setStatus(StormApplicationConstant.SERVER_EXCEPTION_HTTP_STATUS);
        ServletOutputStream os = null;
        try {
            os = context.response.getOutputStream();
            os.write(JsonUtil.objConvert2JsonStr(response)
                    .getBytes(StormApplicationConstant.UTF_8));
            os.flush();
            os.close();
        } catch (IOException e) {
            logger.error("send response failed, message: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
