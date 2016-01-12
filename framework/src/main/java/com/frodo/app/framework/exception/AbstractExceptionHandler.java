package com.frodo.app.framework.exception;

import com.frodo.app.framework.controller.IController;
import com.frodo.app.framework.controller.MainController;

/**
 * Created by frodo on 2015/12/29.
 */
public abstract class AbstractExceptionHandler implements com.frodo.app.framework.exception.ExceptionHandler {
    private MainController mainController;
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;

    public AbstractExceptionHandler(MainController controller) {
        this.mainController = controller;

        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public IController getController() {
        return mainController;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        handle(e);
        if (mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(t, e);
        }
    }
}
