package com.android.app.framework.controller;

/**
 * 子系统接口
 * Created by frodo on 2015/6/20.
 */
public interface ChildSystem {
    IController getController();

    String systemName();
}
