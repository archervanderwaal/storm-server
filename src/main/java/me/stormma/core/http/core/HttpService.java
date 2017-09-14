package me.stormma.core.http.core;

import com.google.common.base.Objects;
import me.stormma.core.config.StormApplicationConfig;
import me.stormma.exception.StormServerException;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.Servlet;
import java.util.*;


/**
 * @author stormma
 * @date 2017/8/13.
 * @description http 服务相关
 */
public class HttpService {

    /**
     * HttpService's instance
     */
    private static HttpService instance;

    /**
     * jetty server
     */
    private static Server jettyServer;

    /**
     * default host
     */
    private static String DEFAULT_HOST = "127.0.0.1";

    /**
     * handlers
     */
    private static List<Handler> handlers;


    /**
     * @description get HttpService's instance
     * @return
     */
    public static HttpService getInstance() {
        if (Objects.equal(null, instance)) {
            synchronized (HttpService.class) {
                if (Objects.equal(null, instance)) {
                    instance = new HttpService();
                }
            }
        }
        return instance;
    }

    /**
     * @description 注册静态文件
     * @param url
     * @param subDir
     * @throws Exception
     */
    public void registerFileService(String url, String subDir) throws StormServerException {
        if (Objects.equal(null, url)) {
            throw new StormServerException("register static file core filed, the url: " + url + " is not valid");
        }
        ResourceHandler handler = new ResourceHandler();
        String resPath = String.format("%s/%s", StormApplicationConfig.MODULE, subDir);
        handler.setResourceBase(resPath);
        ContextHandler context = new ContextHandler(url);
        context.setHandler(handler);
        handlers.add(context);
    }

    /**
     * @description 注册静态文件，绝对路径
     * @param url
     * @param absoluteDirPath
     * @throws Exception
     */
    public void registFileServiceAbsolute(String url, String absoluteDirPath) throws StormServerException {
        if (Objects.equal(null, url)) {
            throw new StormServerException("register static file core filed, the url: " + url + " is not valid");
        }
        ResourceHandler handler = new ResourceHandler();
        handler.setResourceBase(absoluteDirPath);
        ContextHandler context = new ContextHandler(url);
        context.setHandler(handler);
        handlers.add(context);
    }

    /**
     * @description init http core
     * @throws Exception
     */
    public static void init() throws Exception {
        if (jettyServer != null) {
            throw new Exception("HttpServer already initialized.");
        }

        //config thread poll，其中线程池的线程从队列中拿到任务执行，任务队列类型==>ConcurrentLinkedQueue.
        QueuedThreadPool pool = new QueuedThreadPool();
        if (StormApplicationConfig.CUSTOMIZE_THREAD_POOL) {
            pool.setMaxThreads(StormApplicationConfig.MAX_THREAD_COUNT);
            pool.setMinThreads(StormApplicationConfig.MIN_THREAD_COUNT);
            pool.setIdleTimeout(StormApplicationConfig.THREAD_TIMEOUT);
        }
        jettyServer = new Server(pool);
        HttpConfiguration httpConfig = new HttpConfiguration();
        httpConfig.addCustomizer(new ForwardedRequestCustomizer());
        ServerConnector http = new ServerConnector(jettyServer, new HttpConnectionFactory(httpConfig));
        http.setHost(DEFAULT_HOST);
        http.setPort(StormApplicationConfig.PORT);
        http.setIdleTimeout(StormApplicationConfig.IO_TIMEOUT);
        jettyServer.addConnector(http);
        handlers = new LinkedList<>();
        getInstance().removeServerHeader(jettyServer);
    }

    /**
     * @description
     * @param server
     */
    public void removeServerHeader(Server server) {
        for (Connector y : server.getConnectors()) {
            for (ConnectionFactory x : y.getConnectionFactories()) {
                if (x instanceof HttpConnectionFactory) {
                    ((HttpConnectionFactory) x).getHttpConfiguration().setSendServerVersion(false);
                }
            }
        }
    }

    /**
     * @description 注册servlet相等于url和servlet直接的映射
     * @param url
     * @param servlet
     */
    public void registerServlet(String url, Servlet servlet) throws StormServerException {
        if (Objects.equal(null, url)) {
            throw new StormServerException("register servlet failed, url: " + url + " is not valid");
        }
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(url);
        context.addServlet(new ServletHolder(servlet), "/*");
        handlers.add(context);
    }

    /**
     * @description 注册过滤器
     * @param url
     * @param filter
     * @param params
     */
    public void registerFilter(String url, Class<? extends Filter> filter, Map<String, String> params)
                                                                                throws StormServerException {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        if (Objects.equal(null, url)) {
            throw new StormServerException("register filter failed, url: " + url + " is not valid");
        }
        context.setContextPath(url);

        ServletHandler handler = new ServletHandler();
        FilterHolder fh = handler.addFilterWithMapping(filter, "/*", EnumSet.of(DispatcherType.REQUEST));

        if (null != params) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                String value = params.get(key);
                fh.setInitParameter(key, value);
            }
        }
        context.addFilter(fh, "/*", EnumSet.of(DispatcherType.REQUEST));
        context.setHandler(handler);
        handlers.add(context);
    }

    /**
     * @description 启动jetty server
     * @throws Exception
     */
    public static void startJettyServer() throws Exception {
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(handlers.toArray(new Handler[0]));
        jettyServer.setHandler(contexts);
        jettyServer.start();
        jettyServer.join();
    }
}
