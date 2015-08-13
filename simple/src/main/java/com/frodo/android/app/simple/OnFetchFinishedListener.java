package com.frodo.android.app.simple;

/**
 * Created by frodo on 2015/7/26.
 */
@Deprecated
public interface OnFetchFinishedListener<T> {
    void onError(String errorMsg);

    void onSuccess(T resultObject);
}
