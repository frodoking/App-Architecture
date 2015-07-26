package com.frodo.android.app.simple;

import com.frodo.android.app.framework.net.NetworkCallTask;

/**
 * S represent Service for fetch
 * SR represent Server Result
 * LR represent Local Result
 * Created by frodo on 2015/7/26.
 */
public abstract class AbstractFetchTask<S, SR, LR> extends NetworkCallTask<SR> {

    private S service;
    private OnFetchFinishedListener<LR> listener;

    public AbstractFetchTask(S service, OnFetchFinishedListener<LR> l) {
        this.service = service;
        this.listener = l;
    }

    @Override
    public void onError(Exception re) {
        listener.onError(re.getMessage());
    }

    @Override
    public String key() {
        return getClass().getCanonicalName();
    }

    public final S getService() {
        return service;
    }

    public final OnFetchFinishedListener<LR> getListener() {
        return listener;
    }
}
