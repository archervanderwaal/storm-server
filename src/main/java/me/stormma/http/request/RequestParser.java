package me.stormma.http.request;

import com.google.common.base.Objects;
import me.stormma.http.service.HttpContext;
import me.stormma.http.util.HttpUtil;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import java.util.Map;

/**
 * @author stormma
 * @date 2017/8/14.
 * @description 请求解析器
 */
public class RequestParser {

    private static RequestParser instance = new RequestParser();

    private static long MAX_REQUEST_BODY_LENGTH = 1024 * 1024 * 10;

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(RequestParser.class);

    public static void parseRequest(HttpContext context) {
        instance.parseQueryString(context);
        instance.parseCookie(context);
        instance.parseUserAgent(context);
        instance.parseRequestBody(context);
    }

    /**
     * @description 解析query string
     * @param context
     */
    private void parseQueryString(HttpContext context) {
        Map<String, String> param = HttpUtil.parseQueryString(context.request.getQueryString());
        if (!Objects.equal(null, param)) {
            context.params.putAll(param);
        }
    }

    /**
     * @description 解析cookie
     * @param context
     */
    private void parseCookie(HttpContext context) {
        Cookie[] cookies = context.request.getCookies();
        context.cookies = cookies;
    }

    /**
     * @description 解析User-Agent
     * @param context
     */
    private void parseUserAgent(HttpContext context) {
        String userAgent = context.request.getHeader("User-Agent");
        context.userAgent = userAgent;
    }

    /**
     * @description 解析request body
     * @param context
     */
    private void parseRequestBody(HttpContext context) {
        try {
            byte[] body = HttpUtil.readDataFromRequestBody(context, MAX_REQUEST_BODY_LENGTH);
            context.requestBody = body;
        } catch (Exception e) {
            logger.error("parse request body failed, message: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
