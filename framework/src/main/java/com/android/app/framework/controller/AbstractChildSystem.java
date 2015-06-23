package com.android.app.framework.controller;

import com.google.common.base.Preconditions;

/**
 * Base ChildSystem
 * Created by frodo on 2015/6/20.
 */
public abstract class AbstractChildSystem implements ChildSystem {

    private IController controller;

    public AbstractChildSystem(IController controller) {
        this.controller = Preconditions.checkNotNull(controller, "MainController cannot be null");
    }

    @Override
    public IController getController() {
        return this.controller;
    }

    @Override
    public String systemName() {
        return getClass().getSimpleName();
    }
}
