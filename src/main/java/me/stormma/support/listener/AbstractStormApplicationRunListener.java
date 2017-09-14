package me.stormma.support.listener;

import me.stormma.core.config.Environment;

/**
 * @author stormma
 * @date 2017/09/13
 */
public class AbstractStormApplicationRunListener implements StormApplicationRunListener {

    private static volatile int LISTENER_MAX_START_ORDER = Integer.MIN_VALUE;

    @Override
    public void starting() {

    }

    @Override
    public void environmentPrepared(Environment environment) {

    }

    @Override
    public int getStormApplicationRunListenerStartOrder() {
        return getListenerMaxStartOrder();
    }

    public int getListenerMaxStartOrder() {
        LISTENER_MAX_START_ORDER++;
        return LISTENER_MAX_START_ORDER;
    }
}
