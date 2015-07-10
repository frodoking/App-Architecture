package com.frodo.android.app.framework.controller;

import com.google.common.base.Preconditions;

/**
 * Base ChildSystem
 * Created by frodo on 2015/6/20.
 */
public abstract class AbstractChildSystem implements com.frodo.android.app.framework.controller.ChildSystem {

    private com.frodo.android.app.framework.controller.IController controller;

    public AbstractChildSystem(com.frodo.android.app.framework.controller.IController controller) {
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
