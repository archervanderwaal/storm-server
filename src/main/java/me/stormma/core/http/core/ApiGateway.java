package me.stormma.core.http.core;

import com.google.common.base.Objects;
import me.stormma.config.ConfigProperties;
import me.stormma.config.MailConfig;
import me.stormma.config.ServerConfig;
import me.stormma.constant.StormApplicationConstant;
import me.stormma.factory.InstancePool;
import me.stormma.core.http.enums.RequestMethod;
import me.stormma.core.http.handler.Handler;
import me.stormma.core.http.handler.invoker.HandlerInvoker;
import me.stormma.core.http.handler.mapping.HandlerMapping;
import me.stormma.core.http.model.HttpContext;
import me.stormma.core.http.request.RequestParser;
import me.stormma.core.http.response.Response;
import me.stormma.core.http.util.WebUtils;
import me.stormma.mail.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author stormma
 * @date 2017/8/12.
 * @description 自定义网关
 */
public class ApiGateway extends HttpServlet {

    /**
     * ApiGateWay instance
     */
    private static ApiGateway instance;

    private static final Logger logger = LoggerFactory.getLogger(ApiGateway.class);

    /**
     * handler mapping instance
     */
    private static final HandlerMapping handlerMapping = InstancePool.getDefaultHandlerMapping();

    /**
     * handler invoker instance
     */
    private static final HandlerInvoker handlerInvoker = InstancePool.getDefaultHandlerInvoker();

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
     * @description
     * @param request
     * @param response
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        HttpContext context = null;
        try {
            context = createHttpContext(request, response);
        } catch (UnsupportedEncodingException e) {
            logger.error("create HttpContext failed, message: " + e.getMessage());
            return;
        }
        Handler handler = handlerMapping.getHandler(context);
        if (Objects.equal(null, handler)) {
            if (handleOptionsRequest(context)) {
                return;
            }
            WebUtils.send404Response(calculateHandleRequestCostTime(context));
            return;
        }
        Object result;
        try {
            result = handlerInvoker.invoke(context, handler);
        } catch (Exception e) {
            logger.error("invoke " + handler.getMethod() + " failed, " + e.getMessage());
            e.printStackTrace();
            WebUtils.send500Response(calculateHandleRequestCostTime(context));
            return;
        }
        WebUtils.sendSuccessResponse(calculateHandleRequestCostTime(context), (Response) result);
        if (MailConfig.isEnabled) {
            try {
                MailService.sendMessage("ssssss", "ssssss");
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     * @description 创建HttpContext
     */
    private HttpContext createHttpContext(HttpServletRequest request, HttpServletResponse response)
                                                                            throws UnsupportedEncodingException {
        HttpContext context = new HttpContext(request, response);
        context.requestPath = request.getPathInfo();
        RequestParser.parseRequest(context);
        context.request.setCharacterEncoding(StormApplicationConstant.UTF_8);
        context.response.setCharacterEncoding(StormApplicationConstant.UTF_8);
        context.response.setHeader(ConfigProperties.SERVER_ID, ServerConfig.SERVER_ID);
        context.response.setHeader(ConfigProperties.MODULE_NAME, ServerConfig.MODULE_NAME);
        return context;
    }

    /**
     * @description 处理options的请求
     * @param context
     */
    private boolean handleOptionsRequest(HttpContext context) {
        if (Objects.equal(RequestMethod.OPTIONS, context.requestMethod)) {
            context.response.setStatus(StormApplicationConstant.OK_HTTP_STATUS);
            try {
                context.response.getOutputStream().write("OK".getBytes());
            } catch (IOException e) {
                WebUtils.send500Response(context);
            }
            return true;
        }
        return false;
    }

    /**
     * @description 统计请求处理时长
     * @param context
     */
    private HttpContext calculateHandleRequestCostTime(HttpContext context) {
        context.responseEndTime = System.currentTimeMillis();
        context.costTime = (int) (context.responseEndTime - context.requestStartTime);
        return context;
    }
}
