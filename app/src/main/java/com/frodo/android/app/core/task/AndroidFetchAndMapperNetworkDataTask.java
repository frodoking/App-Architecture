package com.frodo.android.app.core.task;

import com.frodo.android.app.framework.net.NetworkCallTask;

import rx.Subscriber;

/**
 * Created by frodo on 2015/8/13. fetch data from net <p/>
 * S: NetService use retrofit  <p/>
 * SR: Service Result  <p/>
 * LR: Locale Result  <p/>
 */
public abstract class AndroidFetchAndMapperNetworkDataTask<S, SR, LR> extends NetworkCallTask<SR> {
    private S service;
    private Subscriber<LR> subscriber;

    protected AndroidFetchAndMapperNetworkDataTask(S service, Subscriber<LR> subscriber) {
        this.service = service;
        this.subscriber = subscriber;
    }

    @Override
    public void onPreCall() {
        subscriber.onStart();
    }

    @Override
    public void onError(Exception re) {
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

    public final Subscriber<LR> getSubscriber() {
        return subscriber;
    }
}
