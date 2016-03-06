package com.frodo.app.framework.net;

import com.frodo.app.framework.exception.HttpException;
import com.frodo.app.framework.task.CallTask;

/**
 * Created by frodo on 2015/7/6. do request and response intercept.
 */
public abstract class NetworkCallTask<R> extends CallTask {

    protected NetworkTransport networkTransport;
    protected Request request;

    protected NetworkCallTask(NetworkTransport networkTransport, Request request) {
        this.networkTransport = networkTransport;
        this.request = request;
    }

    public void onPreCall() {
        if (!networkTransport.interceptorList().isEmpty()) {
            for (NetworkInterceptor interceptor : networkTransport.interceptorList()) {
                if (interceptor instanceof NetworkInterceptor.RequestInterceptor) {
                    ((NetworkInterceptor.RequestInterceptor) interceptor).intercept(request);
                }
            }
        }
    }

    public abstract R doBackgroundCall() throws HttpException;

    public void onSuccess(R result) {
        if (!networkTransport.interceptorList().isEmpty()) {
            for (NetworkInterceptor interceptor : networkTransport.interceptorList()) {
                if (interceptor instanceof NetworkInterceptor.ResponseSuccessInterceptor) {
                    ((NetworkInterceptor.ResponseSuccessInterceptor) interceptor).intercept((String) result);
                }
            }
        }
    }

    public void onError(HttpException re) {
        if (!networkTransport.interceptorList().isEmpty()) {
            for (NetworkInterceptor interceptor : networkTransport.interceptorList()) {
                if (interceptor instanceof NetworkInterceptor.ResponseErrorInterceptor) {
                    ((NetworkInterceptor.ResponseErrorInterceptor) interceptor).intercept(re);
                }
            }
        }
    }

    public void onFinished() {
    }

}
