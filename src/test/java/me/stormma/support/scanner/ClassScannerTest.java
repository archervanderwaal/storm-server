package me.stormma.support.scanner;

import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.Collections;
import java.util.Set;

/**
 * @author stormma
 * @date 2017/8/23
 * @description test
 */
public class ClassScannerTest {

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
}
