package me.stormma.http.core;

import com.google.common.base.Objects;
import me.stormma.StormApplication;
import me.stormma.config.ServerConfig;
import me.stormma.constant.StormApplicationConstant;
import me.stormma.http.handler.RequestHandler;
import me.stormma.http.handler.Handler;
import me.stormma.http.model.HttpContext;
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
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("server_id", ServerConfig.SERVER_ID);
        response.setHeader("module_name", ServerConfig.MODULE_NAME);
        HttpContext context = new HttpContext(request, response);
        context.requestPath = request.getPathInfo();
        RequestParser.parseRequest(context);
        context.request.setCharacterEncoding("UTF-8");
        context.response.setCharacterEncoding("UTF-8");
        Handler handler = StormApplication.apiMap.get(context.requestPath);
        if (Objects.equal(null, handler)) {
            context.response.sendError(StormApplicationConstant.NOT_FOUND_HTTP_STATUS);
            return;
        }
        //RequestHandler.getInstance().handleRequest(context, handler);
    }
}
