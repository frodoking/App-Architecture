package com.frodo.app.framework.controller;

/**
 * Created by frodo on 2016/3/2.
 */
public interface Interceptor<F, T> {
    T intercept(F f);
}
