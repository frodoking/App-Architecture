package com.android.app.framework.log;

import com.android.app.framework.controller.ChildSystem;

/**
 * 对日志操作器
 * Created by frodo on 2015/6/20.
 */
public interface LogCollector extends ChildSystem {
    /**
     * 是否开启本地日志存储并上传功能
     * @param enable
     */
    void enableCollect(boolean enable);

    void logWarn(String tag, String msg );
    void logError(String tag, String msg );
    void logInfo(String tag, String msg );

    /**
     * Register crash handler to handle exception.
     */
    boolean registerCrashHandler();

    /**
     * Unregister crash handler to handle exception.
     */
    boolean unregisterCrashHandler();

    /**
     * Register Object into the stack.
     */
    boolean register(final Object activity);

    /**
     * Unregister Object into the stack.
     */
    boolean unregister(final Object activity);
}
