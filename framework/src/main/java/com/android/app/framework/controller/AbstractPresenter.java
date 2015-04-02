package com.android.app.framework.controller;

import com.google.common.base.Preconditions;

/**
 * Created by frodo on 2015/4/1.
 */
public abstract class AbstractPresenter implements IPresenter {
    private IModel model;
    private IView view;

    protected AbstractPresenter(IView view) {
        this.model = Preconditions.checkNotNull(createModel(), "model cannot be null");
        this.view = view;
    }

    @Override
    public IModel getModel() {
        return model;
    }

    @Override
    public IView getView() {
        return view;
    }

    public abstract IModel createModel();
}
