package com.android.app.framework.net;

import com.android.app.framework.entity.Entity;

/**
 * Created by frodoking on 2014/12/21.
 */
public interface Response {
    void onSuccess(int statusCode, int stat, String msg, Entity data);

    void onFailure(int statusCode, Throwable throwable);
}
