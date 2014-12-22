package com.android.app.framework.net;

import android.util.Log;

import com.android.app.framework.entity.Entity;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * 带有jason的数据返回,并且返回期望的实体数据类型
 * Created by frodoking on 2014/12/19.
 */
public abstract class ResponseHandler extends JsonHttpResponseHandler implements Response {
    private static final String TAG = AsyncRequest.class.getSimpleName();

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);
        Log.i(TAG, " *** statusCode:" + statusCode + ", onSuccess: " + response + " *** ");

        int stat = 0;
        String msg = "";
        Entity entity = null;
        try {
            if (response.has("stat"))
                stat = response.optInt("stat");

            if (response.has("msg"))
                msg = response.optString("msg");

            if (response.has("data")) {
                String responseString;
                if (response.optJSONObject("data") != null) {
                    responseString = response.optJSONObject("data").toString();
                    if (expectedParserType().equals(Request.ParserType.GSON)) {
                        entity = new Gson().fromJson(responseString, expectedClass());
                    } else if (expectedParserType().equals(Request.ParserType.CUSTOM)) {
                        entity = expectedClass().newInstance();
                        entity.fromJson(responseString);
                    }
                } else if (response.optJSONArray("data") != null) {
                    responseString = response.optJSONArray("data").toString();
                    if (expectedParserType().equals(Request.ParserType.GSON)) {
                        entity = new Gson().fromJson(responseString, expectedClass());
                    } else if (expectedParserType().equals(Request.ParserType.CUSTOM)) {
                        entity = expectedClass().newInstance();
                        entity.fromJson(responseString);
                    }
                }
            }

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        onSuccess(statusCode, stat, msg, entity);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
        Log.i(TAG, " *** statusCode:" + statusCode + ", onFailure: " + errorResponse + " *** ");
        onFailure(statusCode, throwable);
    }

    public abstract Class<Entity> expectedClass();

    public abstract Request.ParserType expectedParserType();
}
