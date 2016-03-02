package com.frodo.app.android.core.task;

import com.frodo.app.android.core.toolbox.JsonConverter;
import com.frodo.app.framework.entity.BeanNode;
import com.frodo.app.framework.exception.HttpException;
import com.frodo.app.framework.net.NetworkCallTask;
import com.frodo.app.framework.net.NetworkTransport;
import com.frodo.app.framework.net.Request;
import com.frodo.app.framework.net.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import rx.Subscriber;

/**
 * Created by frodo on 2016/3/1. base bean from server
 */
public class AndroidFetchNetworkDataTask extends NetworkCallTask<BeanNode> {

    private Subscriber<BeanNode> subscriber;

    public AndroidFetchNetworkDataTask(NetworkTransport networkTransport, Request request, Subscriber<BeanNode> subscriber) {
        super(networkTransport, request);
        this.subscriber = subscriber;
    }

    @Override
    public void onPreCall() {
        subscriber.onStart();
    }

    @Override
    public BeanNode doBackgroundCall() throws HttpException {
        Response response = networkTransport.execute(request);
        try {
            InputStream is = response.getBody().in();
            JSONObject jsonObject = new JSONObject(convertStreamToString(is));
            return JsonConverter.convert(jsonObject);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            throw new HttpException(e);
        }
    }

    @Override
    public void onSuccess(BeanNode result) {
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

    public final Subscriber<BeanNode> getSubscriber() {
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
