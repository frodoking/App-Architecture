package com.frodo.android.app;

import java.io.File;
import java.util.concurrent.Executors;

import com.frodo.android.app.core.cache.AndroidCacheSystem;
import com.frodo.android.app.core.filesystem.AndroidFileSystem;
import com.frodo.android.app.core.log.AndroidLogCollectorSystem;
import com.frodo.android.app.core.task.AndroidBackgroundExecutorImpl;
import com.frodo.android.app.core.toolbox.ResourceManager;
import com.frodo.android.app.framework.config.Configuration;
import com.frodo.android.app.framework.context.Context;
import com.frodo.android.app.framework.controller.MainController;
import com.frodo.android.app.framework.controller.ModelFactory;
import com.frodo.android.app.framework.net.NetworkInteractor;
import com.frodo.android.app.framework.scene.Scene;
import com.frodo.android.app.framework.theme.Theme;

import android.app.Application;
import android.os.Environment;

/**
 * Created by frodo on 2014/12/19.
 */
public abstract class AppApplication extends Application implements Context {
    private MainController controller;

    @Override
    public void onCreate() {
        super.onCreate();
        this.controller = new MainController();

        final int numberCores = Runtime.getRuntime().availableProcessors();
        controller.setBackgroundExecutor(new AndroidBackgroundExecutorImpl(Executors.newFixedThreadPool(numberCores * 2 + 1)));

        controller.setContext(this);
        controller.setConfiguration(loadConfiguration());
        controller.setScene(loadScene());
        controller.setTheme(loadTheme());
        controller.setFileSystem(new AndroidFileSystem(controller));
        controller.setNetworkInteractor(loadNetworkInteractor());
        controller.setModelFactory(new ModelFactory());

        enableCache(true);
        enableLogCollector(true);

        ResourceManager.newInstance(this);
    }

    public final MainController getMainController() {
        return this.controller;
    }

    public abstract Configuration loadConfiguration();

    public abstract Scene loadScene();

    public abstract Theme loadTheme();

    public abstract NetworkInteractor loadNetworkInteractor();

    public final void enableCache(boolean enable) {
        if (enable) {
            controller.setCache(new AndroidCacheSystem(this.controller, "/"));
        } else {
            controller.setCache(null);
        }
    }

    public final void enableLogCollector(boolean enable) {
        if (enable) {
            controller.setLogCollector(new AndroidLogCollectorSystem(this.controller));
        } else {
            controller.setLogCollector(null);
        }
    }

    @Override
    public String getRootDirName() {
        return Environment.getExternalStorageDirectory().getPath() + File.separator;
    }

    @Override
    public String getFilesDirName() {
        return getRootDirName() + File.separator + getPackageName() + File.separator;
    }
}
