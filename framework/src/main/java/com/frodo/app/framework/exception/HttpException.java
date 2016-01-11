package com.frodo.app.framework.exception;

/**
 * Created by frodo on 2015/4/2.
 */
public class HttpException extends com.frodo.app.framework.exception.BaseException {
    private static final long serialVersionUID = 1L;

    private int exceptionCode;

    public HttpException() {
    }

    public HttpException(String detailMessage) {
        super(detailMessage);
    }

    public HttpException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public HttpException(Throwable throwable) {
        super(throwable);
    }

    /**
     * @param exceptionCode The http response status code, 0 if the http request error and has no response.
     */
    public HttpException(int exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    /**
     * @param exceptionCode The http response status code, 0 if the http request error and has no response.
     * @param detailMessage
     */
    public HttpException(int exceptionCode, String detailMessage) {
        super(detailMessage);
        this.exceptionCode = exceptionCode;
    }

    /**
     * @param exceptionCode The http response status code, 0 if the http request error and has no response.
     * @param detailMessage
     * @param throwable
     */
    public HttpException(int exceptionCode, String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        this.exceptionCode = exceptionCode;
    }

    /**
     * @param exceptionCode The http response status code, 0 if the http request error and has no response.
     * @param throwable
     */
    public HttpException(int exceptionCode, Throwable throwable) {
        super(throwable);
        this.exceptionCode = exceptionCode;
    }

    /**
     * @return The http response status code, 0 if the http request error and has no response.
     */
    public int getExceptionCode() {
        return exceptionCode;
    }
}

