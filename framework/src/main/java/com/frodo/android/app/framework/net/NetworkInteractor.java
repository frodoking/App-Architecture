package com.frodo.android.app.framework.net;

import com.frodo.android.app.framework.controller.ChildSystem;

/**
 * Created by frodo on 2015/6/20.
 */
public interface NetworkInteractor extends ChildSystem {
    boolean isNetworkAvailable();

    boolean isGpsEnabled();

    boolean isWifiEnabled();

    boolean is3rd();

    boolean isWifi();

    <T> T create(Class<T> service);
}
