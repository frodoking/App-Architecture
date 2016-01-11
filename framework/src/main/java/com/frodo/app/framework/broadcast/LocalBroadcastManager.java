package com.frodo.app.framework.broadcast;

/**
 * Created by frodo on 2015/12/29. 消息中心管理(一定是轻量级的，多模块非必需交互建议采用MainController中转方式)
 */
public interface LocalBroadcastManager {
    void send(Object key);

    void sendAll();

    Object receive(Object key);

    void remove(Object key);

    void removeAll();

    void register(Object key, Object broadcast);
}
