package com.frodo.android.app.framework.exception;

import com.frodo.android.app.framework.controller.IController;
import com.frodo.android.app.framework.controller.MainController;

/**
 * Created by frodo on 2015/12/29.
 */
public abstract class AbstractExceptionHandler implements ExceptionHandler {
    private MainController mainController;
    // 系统默认的异常处理（默认情况下，系统会终止当前的异常程序）
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

    /**
     * 这个是最关键的函数，当程序中有未被捕获的异常，系统将会自动调用#uncaughtException方法
     * thread为出现未捕获异常的线程，ex为未捕获的异常，有了这个ex，我们就可以得到异常信息。
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        handle(e);
        if (mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(t, e);
        }
    }
}
