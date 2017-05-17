package com.frodo.app.android.core.task;

import com.frodo.app.framework.exception.HttpException;
import com.frodo.app.framework.net.NetworkCallTask;
import com.frodo.app.framework.net.NetworkTransport;
import com.frodo.app.framework.net.Request;
import com.frodo.app.framework.net.Response;

import io.reactivex.Emitter;
import io.reactivex.FlowableEmitter;
import io.reactivex.ObservableEmitter;

/**
 * Created by frodo on 2016/3/1. base bean from server
 */
public class AndroidFetchNetworkDataTask extends NetworkCallTask<Response> {

    private Emitter<? super Response> emitter;

    public AndroidFetchNetworkDataTask(NetworkTransport networkTransport, Request request, Emitter<? super Response> emitter) {
        super(networkTransport, request);
        this.emitter = emitter;
    }

    @Override
    public void onPreCall() {
        checkCancelStatus();
        super.onPreCall();
    }

    @Override
    public Response doBackgroundCall() {
        checkCancelStatus();
        try {
            return networkTransport.execute(request);
        } catch (HttpException e) {
            onError(e);
            return null;
        }
    }

    @Override
    public void onSuccess(Response response) {
        checkCancelStatus();
        super.onSuccess(response);
        emitter.onNext(response);
    }

    @Override
    public void onError(HttpException re) {
        super.onError(re);
        emitter.onError(re);
    }

    @Override
    public void onFinished() {
        checkCancelStatus();
        emitter.onComplete();
    }

    @Override
    public String key() {
        return getClass().getSimpleName();
    }

    private void checkCancelStatus() {
        if (emitter instanceof FlowableEmitter) {
            FlowableEmitter flowableEmitter = (FlowableEmitter) emitter;
            if (flowableEmitter.isCancelled()) {
                terminate();
            }
        } else if (emitter instanceof ObservableEmitter) {
            ObservableEmitter observableEmitter = (ObservableEmitter) emitter;
            if (observableEmitter.isDisposed()) {
                terminate();
            }
        }
    }
}
