package com.android.app.core.config;

import com.android.app.framework.config.Configuration;
import com.android.app.framework.controller.AbstractChildSystem;
import com.android.app.framework.controller.IController;

import android.content.Context;

/**
 * Created by frodo on 2015/6/20.
 */
public class AndroidConfig extends AbstractChildSystem implements Configuration{

    private Context context;
    public AndroidConfig(IController controller) {
        super(controller);
        this.context = (Context) controller.getContext();
    }

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public String getVersionName() {
        return null;
    }

    @Override
    public int getVersionCode() {
        return 0;
    }

    @Override
    public boolean isDebug() {
        return false;
    }
}
