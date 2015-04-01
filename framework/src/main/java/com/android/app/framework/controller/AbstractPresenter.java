package com.android.app.framework.controller;

/**
 * Created by frodo on 2015/4/1.
 */
public abstract class AbstractPresenter implements IPresenter {
    private IModel model;
    private IView view;

    protected AbstractPresenter(IView view) {
        this.model = createModel();
        this.view = view;
    }

    @Override
    public final IModel getModel() {
        return model;
    }

    @Override
    public final IView getView() {
        return view;
    }

    public abstract IModel createModel();
}
