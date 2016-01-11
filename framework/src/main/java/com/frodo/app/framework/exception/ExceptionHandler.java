package com.frodo.app.framework.exception;

import com.frodo.app.framework.controller.IController;

/**
 * Created by frodo on 2015/12/29.
 * 异常处理：所有业务拆分成多个bundles，但如果有一些bundles比如造成闪退了，这时我们就需要做一些故障隔离，就是通过统一的Framework Exception Handler来做这个事情。
 * 主线程的Crash；工作线程的异常（网络、存储、其他）。
 */
public interface ExceptionHandler extends Thread.UncaughtExceptionHandler {
    String CRASH_LOG_DIR = "crash_log_dir";

    IController getController();

    void handle(Throwable e);
}
