package com.frodo.android.app.core;

import com.frodo.android.app.framework.controller.Notifier;

import android.content.Context;
import android.os.Handler;

/**
 * 只需要在ui中做通知的回调接口
 * Created by frodo on 2015/4/2.
 */
public abstract class MainUINotifier implements Notifier {

    private Context mContext;
    private Handler mHandler;

    public MainUINotifier(Context context) {
        super();
        this.mContext = context;
    }

    public abstract void onUiNotify(Object... args);

    @Override
    public final void onNotify(final Object... args) {
        getHandler().post(new Runnable() {

            @Override
            public void run() {
                onUiNotify(args);
            }
        });
    }

    private Handler getHandler() {
        return mHandler == null ? mHandler = new Handler(mContext.getMainLooper()) : mHandler;
    }

    public Context getContext() {
        return mContext;
    }
}
