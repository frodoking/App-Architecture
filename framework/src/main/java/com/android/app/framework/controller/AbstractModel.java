package com.android.app.framework.controller;

/**
 * Created by frodo on 2015/4/1.
 */
public abstract class AbstractModel implements IModel {

    private MainController mainController;
    public AbstractModel(MainController controller) {
        this.mainController = controller;
        controller.getModelFactory().registerMode(this);
    }

    @Override
    public String name() {
        return MODEL_UNKNOWN;
    }

    @Override
    public final MainController getMainController() {
        return mainController;
    }
}
