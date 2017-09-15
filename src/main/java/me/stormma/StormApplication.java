package me.stormma;

import me.stormma.annotation.Application;
import me.stormma.annotation.ComponentScan;
import me.stormma.constant.StormApplicationConstant;
import me.stormma.core.config.Environment;
import me.stormma.core.config.StormApplicationConfig;
import me.stormma.factory.InstancePool;
import me.stormma.fault.InitializationError;
import me.stormma.core.http.core.ApiGateway;
import me.stormma.core.http.core.HttpService;
import me.stormma.support.listener.*;
import me.stormma.support.scanner.IClassScanner;
import me.stormma.support.utils.ClassUtils;
import me.stormma.support.utils.EnvironmentUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author stormma
 * @date 2017/8/13.
 * @description storm-server主类
 */
public class StormApplication {

    private static final Logger logger = LoggerFactory.getLogger(StormApplication.class);

    private static ApiGateway apiGateway;

    private static IClassScanner classScanner = InstancePool.getClassScanner();

    /**
     * @param args
     * @description
     */
    public static void run(String[] args) {
        Class<?> clazz = ClassUtils.getPreCallClass(3);
        Environment environment = createStormApplicationEnvironment(args != null && args.length > 1 ? args[0] : null, clazz);
        StormApplicationRunListeners listeners = getListeners(environment);
        listeners.environmentPrepared();
        listeners.starting();
        logger.info(String.format("Starting %s on %s (%s) by %s", clazz.getName().substring(clazz.getName()
                        .lastIndexOf(".") + 1, clazz.getName().length()), EnvironmentUtils.getOsName(), EnvironmentUtils.getProjectOutputDir(),
                EnvironmentUtils.getAuthor()));
        try {
            startService(environment.getStormApplicationComponentScanPackage());
        } catch (Exception e) {
            logger.error("start storm-server core service failed, {}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param basePackageName
     * @description 启动服务
     */
    private static void startService(String basePackageName) throws Exception {
        apiGateway = ApiGateway.getInstance();
        HttpService.init();
        HttpService.getInstance().registerServlet("/", apiGateway);
        logger.info("storm-server start success and listen on " + StormApplicationConfig.PORT);
        HttpService.startJettyServer();
    }

    /**
     * 创建Environment
     *
     * @param args
     * @return
     */
    private static Environment createStormApplicationEnvironment(String args, Class<?> sourceClass) {
        ComponentScan componentScan = sourceClass.getAnnotation(ComponentScan.class);
        Application application = sourceClass.getAnnotation(Application.class);
        String className = sourceClass.getName();
        if (componentScan == null && application == null) {
            throw new InitializationError(String.format("%s no Application and ComponentScan annotation", className));
        }
        String basePackageName;
        if (componentScan != null) {
            basePackageName = componentScan.value().equals("") ?
                    className.substring(0, className.lastIndexOf(".")) : componentScan.value();
        } else {
            basePackageName = application.value().getName()
                    .substring(0, application.value().getName().lastIndexOf("."));
        }
        args = args == null ? StormApplicationConstant.DEFAULT_CONFIG_PATH : args;
        Environment environment = new Environment(args, basePackageName, sourceClass);
        environment.setLogger(logger);
        return environment;
    }

    /**
     * 创建StormApplicationRunListener实例
     *
     * @param environment
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static StormApplicationRunListeners getListeners(Environment environment) {
        Set<Class<? extends StormApplicationRunListener>> listenerClasses = classScanner.getSubClassesOf("me.stormma", StormApplicationRunListener.class);
        if (!environment.getStormApplicationComponentScanPackage().equals("me.stormma")) {
            listenerClasses.addAll(classScanner.getSubClassesOf(environment.getStormApplicationComponentScanPackage(), StormApplicationRunListener.class));
        }
        Set<Class<? extends StormApplicationRunListener>> removeListeners = new HashSet<>();
        for (Class<? extends StormApplicationRunListener> clazz : listenerClasses) {
            if (AbstractStormApplicationRunListener.class == clazz || StormApplicationEnvironmentRunListener.class == clazz
                    || StormApplicationBannerRunListener.class == clazz || StormApplicationContextRunListener.class == clazz) {
                removeListeners.add(clazz);
            }
        }
        listenerClasses.removeAll(removeListeners);
        LinkedList<StormApplicationRunListener> listeners = new LinkedList<>();
        for (Class<? extends StormApplicationRunListener> clazz : listenerClasses) {
            Object obj = null;
            try {
                obj = clazz.newInstance();
            } catch (Exception e) {
                logger.error("reflect {} new instance failed. {}", clazz.getName(), e.getMessage());
            }
            listeners.add(StormApplicationRunListener.class.cast(obj));
        }
        listeners.sort(new Comparator<StormApplicationRunListener>() {
            @Override
            public int compare(StormApplicationRunListener o1, StormApplicationRunListener o2) {
                return o1.getStormApplicationRunListenerStartOrder() - o2.getStormApplicationRunListenerStartOrder();
            }
        });
        //StormApplicationEnvironmentRunListener first
        //StormApplicationBannerRunListener second
        //StormApplicationContextRunListener third
        listeners.addFirst(new StormApplicationContextRunListener());
        listeners.addFirst(new StormApplicationBannerRunListener());
        listeners.addFirst(new StormApplicationEnvironmentRunListener());
        return new StormApplicationRunListeners(listeners, environment);
    }
}
