package com.android.app.framework.controller;

/**
 * 回调接口
 *
 * @author frodoking
 * @date 2014年11月26日 上午11:52:47
 */
public interface Notifier {
    void onNotify(Object... args);
}