package me.stormma;

import me.stormma.annotation.Application;
import me.stormma.annotation.ComponentScan;
import me.stormma.core.config.Environment;
import me.stormma.factory.InstancePool;
import me.stormma.support.listener.AbstractStormApplicationRunListener;
import me.stormma.support.listener.StormApplicationRunListener;
import me.stormma.support.listener.StormApplicationRunListeners;
import me.stormma.support.scanner.IClassScanner;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @author stormma
 * @date 2017/8/14.
 */
@ComponentScan
@Application(StormApplicationTest.class)
public class StormApplicationTest {

    private static final Logger logger = LoggerFactory.getLogger(StormApplicationTest.class);

    private static IClassScanner classScanner = InstancePool.getClassScanner();

    public static void main(String[] args) {
        StormApplication.run(args);
    }

    @Test
    public void testGetListeners() throws IllegalAccessException, InstantiationException {
        Environment environment = new Environment(null, "me.stormma", this.getClass());
        Set<Class<? extends StormApplicationRunListener>> listenerClasses = classScanner.getSubClassesOfClassPath(StormApplicationRunListener.class);
        Set<Class<? extends StormApplicationRunListener>> removeListeners = new HashSet<>();
        for (Class<? extends StormApplicationRunListener> clazz : listenerClasses) {
            if (AbstractStormApplicationRunListener.class == clazz) {
                removeListeners.add(clazz);
            }
        }
        listenerClasses.removeAll(removeListeners);
        Set<StormApplicationRunListener> listeners = new HashSet<>();
        for (Class<? extends StormApplicationRunListener> clazz: listenerClasses) {
            Object obj = clazz.newInstance();
            listeners.add(StormApplicationRunListener.class.cast(obj));
        }
        StormApplicationRunListeners stormApplicationRunListeners = new StormApplicationRunListeners(listeners, environment);
        for (StormApplicationRunListener listener: stormApplicationRunListeners.getListeners()) {
            logger.info("{}", listener.getStormApplicationRunListenerStartOrder());
        }
    }
}
