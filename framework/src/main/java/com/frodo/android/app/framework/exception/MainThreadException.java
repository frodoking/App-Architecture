package com.frodo.android.app.framework.exception;

/**
 * Created by frodo on 2015/12/29. 主线程中的异常
 */
public class MainThreadException extends BaseException {
    private static final long serialVersionUID = 1L;

    public MainThreadException() {
    }

    public MainThreadException(String detailMessage) {
        super(detailMessage);
    }

    public MainThreadException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public MainThreadException(Throwable throwable) {
        super(throwable);
    }
}