package com.frodo.app.framework.log;

import com.frodo.app.framework.controller.ChildSystem;

import java.io.File;

/**
 *  Monitoring logs mainly user behavior monitoring,
 *  quality control (including crash, speed, flow, power),
 *  security monitoring, and diagnostic logging.
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
     * whether to turn on/off collect log to server
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
