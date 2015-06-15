package com.android.app.framework.controller;

import com.google.common.base.Preconditions;

/**
 * Created by frodo on 2015/4/1.
 */
public abstract class AbstractPresenter implements IPresenter {
    private IModel model;
    private IView view;

    protected AbstractPresenter(IView view) {
        this.view = view;
    }

    @Override
    public final IModel getModel() {
        Preconditions.checkNotNull(model, "model cannot be null");
        return model;
    }

    public final void setModel(IModel model){
        this.model = model;
    }

    @Override
    public final IView getView() {
        return view;
    }
}
