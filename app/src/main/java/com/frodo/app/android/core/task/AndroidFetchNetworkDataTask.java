package com.frodo.app.android.core.task;

import com.frodo.app.framework.exception.HttpException;
import com.frodo.app.framework.net.NetworkCallTask;
import com.frodo.app.framework.net.NetworkTransport;
import com.frodo.app.framework.net.Request;
import com.frodo.app.framework.net.Response;

import org.reactivestreams.Subscriber;

/**
 * Created by frodo on 2016/3/1. base bean from server
 */
public class AndroidFetchNetworkDataTask extends NetworkCallTask<Response> {

    private Subscriber<? super Response> subscriber;

    public AndroidFetchNetworkDataTask(NetworkTransport networkTransport, Request request, Subscriber<? super Response> subscriber) {
        super(networkTransport, request);
        this.subscriber = subscriber;
    }

    @Override
    public void onPreCall() {
        super.onPreCall();
    }

    @Override
    public Response doBackgroundCall() {
        try {
            return networkTransport.execute(request);
        } catch (HttpException e) {
            onError(e);
            return null;
        }
    }

    @Override
    public void onSuccess(Response response) {
        super.onSuccess(response);
        subscriber.onNext(response);
    }

    @Override
    public void onError(HttpException re) {
        super.onError(re);
        subscriber.onError(re);
    }

    @Override
    public void onFinished() {
        subscriber.onComplete();
    }

    @Override
    public String key() {
        return getClass().getSimpleName();
    }
}
