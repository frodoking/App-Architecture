package com.frodo.app.framework.controller;

import com.google.common.base.Preconditions;

/**
 * Base ChildSystem
 * Created by frodo on 2015/6/20.
 */
public abstract class AbstractChildSystem implements ChildSystem {

    private IController mController;

    public AbstractChildSystem(IController controller) {
        this.mController = Preconditions.checkNotNull(controller, "MainController cannot be null");
    }

    public IController getController() {
        return this.mController;
    }

    @Override
    public String systemName() {
        return getClass().getSimpleName();
    }
}
