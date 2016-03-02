package com.frodo.app.framework.net;

import com.frodo.app.framework.controller.ChildSystem;
import com.frodo.app.framework.controller.Interceptor;
import com.frodo.app.framework.exception.HttpException;

/**
 * Created by frodo on 2015/6/20. network request master
 */
public interface NetworkTransport extends ChildSystem {
    boolean isNetworkAvailable();

    boolean isGpsEnabled();

    boolean isWifiEnabled();

    boolean is3rd();

    boolean isWifi();

    String getAPIUrl();

    void setAPIUrl(String apiUrl);

    Response execute(Request request) throws HttpException;

   void addInterceptor(Interceptor interceptor);
}
