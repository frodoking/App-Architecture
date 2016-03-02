package com.frodo.app.framework.net;

import com.frodo.app.framework.controller.Interceptor;
import com.frodo.app.framework.entity.BeanNode;
import com.frodo.app.framework.exception.HttpException;

/**
 * Created by frodo on 2016/3/2.
 */
public class NetworkInterceptor {

    public static class RequestInterceptor implements Interceptor<Request, Void> {

        @Override
        public Void intercept(Request request) {
            return null;
        }
    }

    public static class ResponseSuccessInterceptor implements Interceptor<String, BeanNode> {

        @Override
        public BeanNode intercept(String s) {
            return null;
        }
    }

    public static class ResponseErrorInterceptor implements Interceptor<HttpException, String> {


        @Override
        public String intercept(HttpException e) {
            return null;
        }
    }
}
