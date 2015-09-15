package com.frodo.android.app.core.task;

import com.frodo.android.app.framework.exception.HttpException;
import com.frodo.android.app.framework.net.NetworkCallTask;

import rx.Subscriber;

/**
 * Created by frodo on 2015/8/15. fetch data from net and no need transform data
 */
public abstract class AndroidFetchNetworkDataTask<S, R> extends NetworkCallTask<R> {
    private S service;
    private Subscriber<R> subscriber;

    protected AndroidFetchNetworkDataTask(S service, Subscriber<R> subscriber) {
        this.service = service;
        this.subscriber = subscriber;
    }

    @Override
    public void onPreCall() {
        subscriber.onStart();
    }

    @Override
    public void onError(HttpException re) {
        subscriber.onError(re);
    }

    @Override
    public void onFinished() {
        subscriber.onCompleted();
    }

    @Override
    public String key() {
        return getClass().getCanonicalName();
    }

    public final S getService() {
        return service;
    }

    public final Subscriber<R> getSubscriber() {
        return subscriber;
    }
}
