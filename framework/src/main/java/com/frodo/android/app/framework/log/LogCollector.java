package com.frodo.android.app.framework.log;

import com.frodo.android.app.framework.controller.ChildSystem;

import java.io.File;

/**
 * 对日志操作器
 * Created by frodo on 2015/6/20.
 */
public interface LogCollector extends ChildSystem {

    // Log levels
    int VERBOSE = 2;
    int DEBUG = 3;
    int INFO = 4;
    int WARN = 5;
    int ERROR = 6;
    int ASSERT = 7;
    int NONE = 8;

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
