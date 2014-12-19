package com.android.app.framework.controller;

import android.content.Context;
import android.os.Handler;

/**
 * 只需要在ui中做通知的回调接口
 *
 * @author frodoking
 * @date 2014年11月26日 下午2:44:25
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
