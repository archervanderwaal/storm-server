package me.stormma.support.listener;

import me.stormma.core.config.Environment;

import java.util.*;

/**
 * @author stormma
 * @date 2017/09/12
 * 加油，距离春招还有117天..
 */
public class StormApplicationRunListeners {

    private final List<StormApplicationRunListener> listeners;

    private final Environment environment;

    public StormApplicationRunListeners(Collection<? extends StormApplicationRunListener> listeners, Environment environment) {
        this.listeners = new LinkedList<>(listeners);
        this.environment = environment;
    }

    public List<StormApplicationRunListener> getListeners() {
        return listeners;
    }

    public void starting() {
        for (StormApplicationRunListener listener : this.listeners) {
            listener.starting();
        }
    }

    public void environmentPrepared() {
        for (StormApplicationRunListener listener : this.listeners) {
            listener.environmentPrepared(environment);
        }
    }
}
