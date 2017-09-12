package me.stormma;

import me.stormma.annotation.Application;
import me.stormma.annotation.ComponentScan;

/**
 * @author stormma
 * @date 2017/8/14.
 */
@ComponentScan
@Application(StormApplicationTest.class)
public class StormApplicationTest {
    public static void main(String[] args) {
        StormApplication.run(args);
    }
}
