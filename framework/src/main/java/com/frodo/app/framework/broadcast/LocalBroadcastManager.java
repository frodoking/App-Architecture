package com.frodo.app.framework.broadcast;

/**
 * Created by frodo on 2015/12/29.  message center , to manage communicate between with subsystem
 */
public interface LocalBroadcastManager {
    void send(Object key);

    void sendAll();

    Object receive(Object key);

    void remove(Object key);

    void removeAll();

    void register(Object key, Object broadcast);
}
