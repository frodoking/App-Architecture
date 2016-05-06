package com.frodo.app.android.core.task;

import com.frodo.app.framework.exception.HttpException;
import com.frodo.app.framework.log.Logger;
import com.frodo.app.framework.net.NetworkCallTask;
import com.frodo.app.framework.net.NetworkTransport;
import com.frodo.app.framework.net.Request;
import com.frodo.app.framework.net.Response;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by frodo on 2016/3/1. base bean from server
 */
public class AndroidFetchNetworkDataTask extends NetworkCallTask<String> {

    private Subscriber<String> subscriber;

    public AndroidFetchNetworkDataTask(NetworkTransport networkTransport, Request request, Subscriber<String> subscriber) {
        super(networkTransport, request);
        this.subscriber = subscriber;
    }

    @Override
    public void onPreCall() {
        super.onPreCall();
        subscriber.onStart();
    }

    @Override
    public String doBackgroundCall() throws HttpException {
        Response response = networkTransport.execute(request);
        try {
            String s = ((ResponseBody)response.getBody()).string();
            Logger.fLog().tag(key()).i("Response String : " + s);
            return  s;
        } catch (Exception e) {
            Logger.fLog().tag(key()).e("Response Exception : ", e);
            throw new HttpException(e);
        }
    }

    @Override
    public void onSuccess(String result) {
        super.onSuccess(result);
        subscriber.onNext(result);
    }

    @Override
    public void onError(HttpException re) {
        super.onError(re);
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

    public final Subscriber<String> getSubscriber() {
        return subscriber;
    }
}
