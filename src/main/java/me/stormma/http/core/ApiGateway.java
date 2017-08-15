package me.stormma.http.core;

import com.google.common.base.Objects;
import me.stormma.StormApplication;
import me.stormma.config.ConfigProperties;
import me.stormma.config.ServerConfig;
import me.stormma.constant.StormApplicationConstant;
import me.stormma.factory.InstancePool;
import me.stormma.http.enums.RequestMethod;
import me.stormma.http.handler.RequestHandler;
import me.stormma.http.handler.Handler;
import me.stormma.http.handler.invoker.HandlerInvoker;
import me.stormma.http.handler.mapping.HandlerMapping;
import me.stormma.http.helper.ApplicationHelper;
import me.stormma.http.model.HttpContext;
import me.stormma.http.request.RequestParser;
import me.stormma.http.response.Response;
import me.stormma.http.response.builder.ResponseBuilder;
import me.stormma.http.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //create HttpContext
        HttpContext context = createHttpContext(request, response);

        Handler handler = handlerMapping.getHandler(context);
        if (Objects.equal(null, handler)) {
            if (handleOptionsRequest(context)) {
                return;
            }
            context.response.setStatus(StormApplicationConstant.NOT_FOUND_HTTP_STATUS);
            Response<String> responseData = ResponseBuilder.fail(StormApplicationConstant.NOT_FOUND_HTTP_STATUS_DESC);
            //response 404 http status and return json data
            context.response.getOutputStream().write(JsonUtil.objConvert2JsonStr(responseData)
                                                                            .getBytes(StormApplicationConstant.UTF_8));
            return;
        }

    }

    /**
     * @description 创建HttpContext
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    private HttpContext createHttpContext(HttpServletRequest request, HttpServletResponse response) {
        HttpContext context = new HttpContext(request, response);
        context.requestPath = request.getPathInfo();
        RequestParser.parseRequest(context);
        try {
            context.request.setCharacterEncoding(StormApplicationConstant.UTF_8);
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException==>{}", StormApplicationConstant.UTF_8);
            throw new RuntimeException(e);
        }
        context.response.setCharacterEncoding(StormApplicationConstant.UTF_8);
        context.response.setHeader(ConfigProperties.SERVER_ID, ServerConfig.SERVER_ID);
        context.response.setHeader(ConfigProperties.MODULE_NAME, ServerConfig.MODULE_NAME);
        return context;
    }

    /**
     * @description 处理options的请求
     */
    private boolean handleOptionsRequest(HttpContext context) {
        if (Objects.equal(RequestMethod.OPTIONS, context.requestMethod)) {
            if (handlerMapping.validateRequestPath(context)) {
                context.response.setStatus(StormApplicationConstant.OK_HTTP_STATUS);
            } else {
                context.response.setStatus(StormApplicationConstant.NOT_FOUND_HTTP_STATUS);
            }
            return true;
        } else {
            return false;
        }
    }
}
