package com.frodo.app.framework.controller;

/**
 * Created by frodo on 2015/4/1.
 */
public abstract class AbstractModel implements com.frodo.app.framework.controller.IModel {

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

    public static class SimpleModel extends AbstractModel {

        public SimpleModel(MainController controller) {
            super(controller);
        }

        @Override
        public void initBusiness() {
            // do nothing
        }
    }
}
