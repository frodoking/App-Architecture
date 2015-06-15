package com.android.app;

import android.app.Application;

import com.android.app.framework.config.Configuration;
import com.android.app.framework.controller.MainController;
import com.android.app.framework.datasource.StorageSystems;
import com.android.app.framework.scene.Scene;
import com.android.app.framework.theme.Theme;

/**
 * Created by frodo on 2014/12/19.
 */
public abstract class AppApplication extends Application {
    private MainController controller;

    @Override
    public void onCreate() {
        super.onCreate();
        controller = new MainController(getConfiguration(), getScene(), getAppTheme(), getStorageSystems());
    }

    public MainController getMainController() {
        return controller;
    }

    public abstract Scene getScene();

    public abstract Theme getAppTheme();

    public abstract Configuration getConfiguration();

    public abstract StorageSystems getStorageSystems();
}
