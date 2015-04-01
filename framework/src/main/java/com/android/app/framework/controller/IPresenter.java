package com.android.app.framework.controller;

/**
 * 用于UI的mvp模式中的p
 *
 * v和p保持一一对应，而多个p对应一个m的关系
 * Created by frodo on 2015/4/1.
 */
public interface IPresenter {
    IView getView();

    IModel getModel();
}
