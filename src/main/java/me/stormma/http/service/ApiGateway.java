package me.stormma.http.service;

import com.google.common.base.Objects;
import me.stormma.SmartApplication;
import me.stormma.config.ServerConfig;
import me.stormma.http.handler.RequestHandler;
import me.stormma.http.model.ExecutorBean;
import me.stormma.http.request.RequestParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

/**
 * @author stormma
 * @date 2017/8/12.
 * @description 自定义网关
 */
public class ApiGateway extends HttpServlet {

    private static ApiGateway instance;

    private static final Logger logger = LoggerFactory.getLogger(ApiGateway.class);

    private ApiGateway() {
    }

    public static ApiGateway getInstance() {
        if (Objects.equal(null, instance)) {
            synchronized (ApiGateway.class) {
                if (Objects.equal(null, instance)) {
                    instance = new ApiGateway();
                }
            }
        }
        return instance;
    }

    /**
     * 重写service实现请求的分发
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("server_id", ServerConfig.SERVER_ID);
        resp.setHeader("module_name", ServerConfig.MODULE_NAME);
        HttpContext context = new HttpContext(req, resp);
        context.requestPath = req.getPathInfo();
        RequestParser.parseRequest(context);
        context.request.setCharacterEncoding("UTF-8");
        context.response.setCharacterEncoding("UTF-8");
        ExecutorBean bean = SmartApplication.apiMap.get(context.requestPath);
        try {
            RequestHandler.getInstance().handleRequest(context, bean);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
