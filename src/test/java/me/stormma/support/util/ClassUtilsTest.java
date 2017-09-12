package me.stormma.support.util;

import me.stormma.support.utils.ClassUtils;
import org.junit.Test;

/**
 * @author stormma
 * @date 2017/09/12
 */
public class ClassUtilsTest {
    @Test
    public void testGetPreCallClass() throws ClassNotFoundException {
        Class<?> clazz = ClassUtils.getPreCallClass(3);
        System.out.println(clazz.getName());
    }
}
