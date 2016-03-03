package com.frodo.app.android.core.task;

import com.frodo.app.framework.exception.HttpException;
import com.frodo.app.framework.net.NetworkCallTask;
import com.frodo.app.framework.net.NetworkTransport;
import com.frodo.app.framework.net.Request;
import com.frodo.app.framework.net.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
            InputStream is = response.getBody().in();
            return convertStreamToString(is);
        } catch (IOException e) {
            e.printStackTrace();
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

    private static String convertStreamToString(InputStream is) throws IOException {
        // json is UTF-8 by default
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }

        is.close();

        return sb.toString();
    }
}
