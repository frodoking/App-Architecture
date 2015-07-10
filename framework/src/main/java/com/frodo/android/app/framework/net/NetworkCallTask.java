package com.frodo.android.app.framework.net;

/**
 * Created by frodo on 2015/7/6.
 */
public abstract class NetworkCallTask<R> {

    public void onPreCall() {
    }

    public abstract R doBackgroundCall() throws Exception;

    public abstract void onSuccess(R result);

    public abstract void onError(Exception re);

    public void onFinished() {
    }

}
