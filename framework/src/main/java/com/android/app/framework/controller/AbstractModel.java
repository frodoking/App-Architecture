package com.android.app.framework.controller;

/**
 * Created by frodo on 2015/4/1.
 */
public abstract class AbstractModel implements IModel {

    public AbstractModel() {
        getMainController().registerMode(this);
    }

    @Override
    public String name() {
        return MODEL_UNKNOWN;
    }
}
