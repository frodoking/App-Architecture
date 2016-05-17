package com.frodo.app.framework.controller;

/**
 * SubSystem interface
 * Created by frodo on 2015/6/20.
 */
public interface ChildSystem {
    IController getmController();

    String systemName();
}
