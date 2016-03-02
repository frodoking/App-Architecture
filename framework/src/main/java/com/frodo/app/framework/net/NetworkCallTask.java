package com.frodo.app.framework.net;

import com.frodo.app.framework.exception.HttpException;
import com.frodo.app.framework.task.CallTask;

/**
 * Created by frodo on 2015/7/6.
 */
public abstract class NetworkCallTask<R> extends CallTask {

    protected NetworkTransport networkTransport;
    protected Request request;

    protected NetworkCallTask(NetworkTransport networkTransport, Request request) {
        this.networkTransport = networkTransport;
        this.request = request;
    }

    public void onPreCall() {
    }

    public abstract R doBackgroundCall() throws HttpException;

    public abstract void onSuccess(R result);

    public abstract void onError(HttpException re);

    public void onFinished() {
    }

}
