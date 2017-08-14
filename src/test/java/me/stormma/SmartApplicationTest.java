package me.stormma;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author stormma
 * @date 2017/8/14.
 */
public class SmartApplicationTest {

    public static void main(String[] args) throws Exception {
        SmartApplication.run(args, "me.stormma.controller");
    }
}
