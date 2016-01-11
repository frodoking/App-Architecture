package com.frodo.app.framework.controller;

/**
 * 用于UI的mvp模式中的p
 * <p>
 * v和p保持一一对应，而多个p对应一个m的关系
 * Created by frodo on 2015/4/1.
 */
public interface UIViewController<V extends com.frodo.app.framework.controller.IView, M extends IModel> {
    V getUIView();

    M getModel();
}
