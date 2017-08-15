package me.stormma.http.util;

import com.google.common.base.Objects;
import me.stormma.exception.StormServerException;
import me.stormma.http.model.HttpContext;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.UrlEncoded;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * @author stormma
 * @date 2017/8/13.
 * @description http 工具类
 */
public class HttpUtil {

    /**
     * @param request
     * @return
     * @description get real ip from request
     */
    public static String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (Objects.equal(null, ip) || Objects.equal(ip.length(), 0) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            if (Objects.equal(null, ip) || Objects.equal(ip.length(), 0) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
                if (Objects.equal(null, ip) || Objects.equal(ip.length(), 0) || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getRemoteAddr();
                }
            }
        }
        return ip;
    }

    public static Map<String, Object> parseQueryString(String queryString) {
        if (Objects.equal(null, queryString) || Objects.equal(0, queryString.length())) {
            return null;
        }
        MultiMap<String> param = new MultiMap<String>();
        byte[] bytes = queryString.getBytes();
        UrlEncoded.decodeUtf8To(bytes, 0, queryString.length(), param);
        if (Objects.equal(0, param.size())) {
            return null;
        }
        Map<String, Object> data = new HashMap<String, Object>();
        Enumeration<String> enumeration = Collections.enumeration(param.keySet());
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            List<String> values = param.getValues(name);
            if (Objects.equal(null, values)) {
                data.put(name, param.getValue(name, 0));
            } else {
                data.put(name, values);
            }
        }
        return data;
    }

    /**
     * @param context
     * @param MAX_REQUEST_BODY_LENGTH
     * @return
     * @description 读取body的数据
     */
    public static byte[] readDataFromRequestBody(HttpContext context, long MAX_REQUEST_BODY_LENGTH)
            throws StormServerException, IOException {
        int bodySize = context.request.getContentLength();
        if (bodySize > MAX_REQUEST_BODY_LENGTH) {
            throw new StormServerException("request body too large, length: " + bodySize);
        } else if (Objects.equal(-1, bodySize)) {
            bodySize = 0;
        }
        byte[] body = new byte[bodySize];
        ServletInputStream inputStream = context.request.getInputStream();
        int pos = 0;
        while ((pos < bodySize)) {
            int received = inputStream.read(body, pos, bodySize - pos);
            if (Objects.equal(-1, received)) {
                break;
            }
            pos += received;
        }

        if (pos != bodySize) {
            throw new StormServerException(String.format("Client sent less data than expected, expected: %s cur: %s",
                    bodySize, pos));
        }
        return body;
    }
}
