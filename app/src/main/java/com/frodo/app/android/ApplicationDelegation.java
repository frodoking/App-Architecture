package com.frodo.app.android;

import com.frodo.app.framework.controller.MainController;

/**
 * Created by frodo on 2016/6/15. delegation Application
 */

public interface ApplicationDelegation {
    void beforeLoad();

    MicroContextLoader loadMicroContextLoader();

    void afterLoad();

    MainController getMainController();
}
