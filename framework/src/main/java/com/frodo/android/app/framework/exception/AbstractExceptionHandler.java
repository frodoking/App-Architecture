package com.frodo.android.app.framework.exception;

import com.frodo.android.app.framework.controller.IController;
import com.frodo.android.app.framework.controller.MainController;

/**
 * Created by frodo on 2015/12/29.
 */
public abstract class AbstractExceptionHandler implements ExceptionHandler {
    private MainController mainController;

    public AbstractExceptionHandler(MainController controller) {
        this.mainController = controller;
    }

    @Override
    public IController getController() {
        return mainController;
    }

    public static final class SimpleExceptionHandler extends AbstractExceptionHandler {

        public SimpleExceptionHandler(MainController controller) {
            super(controller);
        }

        @Override
        public void handle(Exception e) {
            // do nothing...
        }
    }
}
