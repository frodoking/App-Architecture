package com.frodo.app.framework.broadcast;

import com.frodo.app.framework.controller.Interceptor;

/**
 * Created by frodo on 2015/12/29.  message center , to manage communicate between with subsystem
 */
public interface LocalBroadcastManager {
    void onBroadcast(String group, Object message);

    void onBroadcastAll(Object message);

    void unRegister(String group, MessageInterceptor listener);

    void unRegisterGroup(String group);

    void unRegisterAll();

    void register(String group, MessageInterceptor listener);

    interface MessageInterceptor extends Interceptor<Object, Boolean> {
    }
}
