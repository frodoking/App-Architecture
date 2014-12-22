package com.android.app.framework.datasource;

import android.content.Context;

import com.android.app.framework.entity.Entity;
import com.android.app.framework.net.AsyncRequest;
import com.android.app.framework.net.Request;
import com.android.app.framework.net.Response;
import com.android.app.framework.net.ResponseHandler;

/**
 * Created by frodoking on 2014/12/21.
 */
public class DataSource {
    private Context mContext;

    public DataSource(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public void doRequest(final Request request, final Response response) {
        final ResponseHandler responseHandler = new ResponseHandler() {
            @Override
            public Class<Entity> expectedClass() {
                return request.getExpectType();
            }

            @Override
            public Request.ParserType expectedParserType() {
                return request.getParserType();
            }

            @Override
            public void onSuccess(int statusCode, int stat, String msg, Entity data) {
                response.onSuccess(statusCode, stat, msg, data);
            }

            @Override
            public void onFailure(int statusCode, Throwable throwable) {
                response.onFailure(statusCode, throwable);
            }
        };


        if (request.getType() == Request.GET) {
            AsyncRequest.doGetRequest(getContext(), request.getPath(), request.getParams(), responseHandler);
        } else if (request.getType() == Request.POST) {
            AsyncRequest.doPostRequest(getContext(), request.getPath(), null, responseHandler);
        }

    }
}
