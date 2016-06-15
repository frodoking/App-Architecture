package com.frodo.app.framework.broadcast;

import com.frodo.app.framework.controller.Interceptor;

/**
 * Created by frodo on 2015/12/29.  message center , to manage communicate between with subsystem
 */
public interface LocalBroadcastManager {
    /**
     * @param group
     * @param message
     */
    void onBroadcast(String group, Object message);

    /**
     * @param message
     */
    void onBroadcastAll(Object message);

    /**
     * @param group
     * @param listener
     */
    void unRegister(String group, MessageInterceptor listener);

    /**
     * @param group
     */
    void unRegisterGroup(String group);


    /**
     *
     */
    void unRegisterAll();

    /**
     * @param group
     * @param listener
     */
    void register(String group, MessageInterceptor listener);

    /**
     *
     */
    interface MessageInterceptor extends Interceptor<Object, Boolean> {
    }
}
