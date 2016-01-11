package com.frodo.app.framework.net;

import com.frodo.app.framework.controller.ChildSystem;

/**
 * Created by frodo on 2015/6/20.网络通信
 */
public interface NetworkTransport extends ChildSystem {
    boolean isNetworkAvailable();

    boolean isGpsEnabled();

    boolean isWifiEnabled();

    boolean is3rd();

    boolean isWifi();

    <T> T create(Class<T> service);
}
