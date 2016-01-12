package com.frodo.app.framework.exception;

import com.frodo.app.framework.controller.IController;

/**
 * Created by frodo on 2015/12/29.
 * Exception handling: All business split into multiple bundles,
 * but if there are bundles such cause flash back, then we need to do some fault isolation,
 * it is through a unified Framework Exception Handler to do this thing.
 * The main thread of the Crash; abnormal worker threads (network, storage, other).
 */
public interface ExceptionHandler extends Thread.UncaughtExceptionHandler {
    String CRASH_LOG_DIR = "crash_log_dir";

    IController getController();

    void handle(Throwable e);
}
