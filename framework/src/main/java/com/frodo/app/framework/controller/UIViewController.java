package com.frodo.app.framework.controller;

/**
 * like UIViewController in IOS API
 * Created by frodo on 2015/4/1.
 */
public interface UIViewController<V extends IView, M extends IModel> {
    V getUIView();

    M getModel();
}
