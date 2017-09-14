package me.stormma.support.listener;

import me.stormma.core.config.Environment;

/**
 * @author stormma
 * @date 2017/09/12
 */
public interface StormApplicationRunListener {

    /**
     * starting
     */
    void starting();

    /**
     * @param environment
     */
    void environmentPrepared(Environment environment);

    /**
     * @return
     */
    int getStormApplicationRunListenerStartOrder();
}
