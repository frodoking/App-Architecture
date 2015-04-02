package com.android.app;

import android.app.Application;

import com.android.app.framework.config.Configuration;
import com.android.app.framework.controller.IController;
import com.android.app.framework.controller.MainController;
import com.android.app.framework.datasource.StorageSystems;

/**
 * Created by frodo on 2014/12/19.
 */
public abstract class AppApplication extends Application {
    private MainController controller;

    @Override
    public void onCreate() {
        super.onCreate();
        controller = new MainController(getConfiguration(), getStorageSystems());
    }

    public MainController getMainController() {
        return controller;
    }

    public abstract Configuration getConfiguration();

    public abstract StorageSystems getStorageSystems();
}
