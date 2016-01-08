package com.frodo.android.app.framework.log;

import com.frodo.android.app.framework.controller.ChildSystem;

import java.io.File;

/**
 * 监控日志主要是用户行为监控，质量监控（包括crash,速度，流量，电量），安全监控以及诊断日志。
 * Created by frodo on 2015/6/20.
 */
public interface LogCollector extends ChildSystem {

    // Log levels
    /**
     * Priority constant for the println method; use Log.v.
     */
    int VERBOSE = 2;

    /**
     * Priority constant for the println method; use Log.d.
     */
    int DEBUG = 3;

    /**
     * Priority constant for the println method; use Log.i.
     */
    int INFO = 4;

    /**
     * Priority constant for the println method; use Log.w.
     */
    int WARN = 5;

    /**
     * Priority constant for the println method; use Log.e.
     */
    int ERROR = 6;

    /**
     * Priority constant for the println method.
     */
    int ASSERT = 7;

    /**
     * 是否开启本地日志存储并上传功能
     *
     * @param enable
     */
    void enableCollect(boolean enable);

    void v(String tag, String message);

    void v(String tag, String message, Throwable t);

    void d(String tag, String message);

    void d(String tag, String message, Throwable t);

    void i(String tag, String message);

    void i(String tag, String message, Throwable t);

    void w(String tag, String message);

    void w(String tag, String message, Throwable t);

    void e(String tag, String message);

    void e(String tag, String message, Throwable t);

    void watchLeak(Object watchedReference);

    void uploadLeakBlocking(File file, String leakInfo);
}
