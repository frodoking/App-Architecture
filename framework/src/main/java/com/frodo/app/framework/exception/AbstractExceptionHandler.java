package com.frodo.app.framework.exception;

import com.frodo.app.framework.controller.IController;
import com.frodo.app.framework.controller.MainController;

/**
 * Created by frodo on 2015/12/29.
 */
public abstract class AbstractExceptionHandler implements ExceptionHandler {
    private MainController mainController;
    private Thread.UncaughtExceptionHandler defaultCrashHandler;

    public AbstractExceptionHandler(MainController controller) {
        this.mainController = controller;

        defaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public IController getController() {
        return mainController;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        handle(e);
        if (defaultCrashHandler != null) {
            defaultCrashHandler.uncaughtException(t, e);
        }
    }
}
