package com.frodo.app.framework.exception;

/**
 * Created by frodo on 2015/12/29. it's exception in main thread.
 */
public class MainThreadException extends com.frodo.app.framework.exception.BaseException {
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