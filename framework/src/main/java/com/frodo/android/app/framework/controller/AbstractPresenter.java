package com.frodo.android.app.framework.controller;

import com.google.common.base.Preconditions;

/**
 * Created by frodo on 2015/4/1.
 */
public abstract class AbstractPresenter implements com.frodo.android.app.framework.controller.IPresenter {
    private com.frodo.android.app.framework.controller.IModel model;
    private com.frodo.android.app.framework.controller.IView view;

    protected AbstractPresenter(com.frodo.android.app.framework.controller.IView view) {
        this.view = view;
    }

    @Override
    public final com.frodo.android.app.framework.controller.IModel getModel() {
        Preconditions.checkNotNull(model, "model cannot be null");
        return model;
    }

    public final void setModel(IModel model) {
        this.model = model;
    }

    @Override
    public final IView getView() {
        return view;
    }
}
