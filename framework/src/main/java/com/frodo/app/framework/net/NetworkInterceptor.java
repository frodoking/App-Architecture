package com.frodo.app.framework.net;

import com.frodo.app.framework.controller.Interceptor;
import com.frodo.app.framework.exception.HttpException;

/**
 * Created by frodo on 2016/3/2.network intercept.
 */
public interface NetworkInterceptor<R, V> extends Interceptor<R, V> {

    class RequestInterceptor implements NetworkInterceptor<Request, Void> {

        @Override
        public Void intercept(Request request) {
            return null;
        }
    }

    class ResponseSuccessInterceptor implements NetworkInterceptor<Response, Void> {

        @Override
        public Void intercept(Response response) {
            return null;
        }
    }

    class ResponseErrorInterceptor implements NetworkInterceptor<HttpException, String> {


        @Override
        public String intercept(HttpException e) {
            return null;
        }
    }
}
