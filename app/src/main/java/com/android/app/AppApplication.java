package com.android.app;

import android.app.Application;

import com.android.app.framework.config.Configuration;
import com.android.app.framework.controller.IController;
import com.android.app.framework.controller.MainController;

/**
 * Created by frodo on 2014/12/19.
 */
public abstract class AppApplication extends Application{
    private IController mController;

    @Override
    public void onCreate() {
        super.onCreate();
        mController = new MainController(getConfiguration());
    }

    public IController getController(){
        return mController;
    }

    public abstract Configuration getConfiguration();
}
