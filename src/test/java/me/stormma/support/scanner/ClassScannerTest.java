package me.stormma.support.scanner;

import me.stormma.factory.InstancePool;
import me.stormma.support.listener.StormApplicationRunListener;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Set;

/**
 * @author stormma
 * @date 2017/8/23
 * @description test
 */
public class ClassScannerTest {

    private static final Logger logger = LoggerFactory.getLogger(ClassScannerTest.class);

    @Test
    public void test() {
        Reflections reflections = new Reflections("me.stormma", new SubTypesScanner(false));
        Set<String> allClassesType = reflections.getAllTypes();
        Set<Class<?>> classes = Collections.EMPTY_SET;
        for (String type : allClassesType) {
            try {
                System.out.println(Class.forName(type));
            } catch (ClassNotFoundException e) {
            }
        }
    }

    @Test
    public void testGetSubType() {
        IClassScanner classScanner = InstancePool.getClassScanner();
        Set<Class<? extends StormApplicationRunListener>> listeners = classScanner.getSubClassesOf("", StormApplicationRunListener.class);
        logger.info("{}", listeners.size());
    }
}
