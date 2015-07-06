package com.android.app;

import java.util.concurrent.Executors;

import com.android.app.core.cache.AndroidCacheSystem;
import com.android.app.core.log.AndroidLogCollectorSystem;
import com.android.app.core.task.BackgroundExecutorImpl;
import com.android.app.core.toolbox.ResourceManager;
import com.android.app.framework.config.Configuration;
import com.android.app.framework.context.Context;
import com.android.app.framework.controller.MainController;
import com.android.app.framework.controller.ModelFactory;
import com.android.app.framework.net.NetworkInteractor;
import com.android.app.framework.scene.Scene;
import com.android.app.framework.theme.Theme;

import android.app.Application;

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
        controller.setBackgroundExecutor(new BackgroundExecutorImpl(Executors.newFixedThreadPool(numberCores * 2 + 1)));

        controller.setContext(this);
        controller.setConfiguration(loadConfiguration());
        controller.setScene(loadScene());
        controller.setTheme(loadTheme());
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
        }
    }

    public final void enableLogCollector(boolean enable) {
        if (enable) {
            controller.setLogCollector(new AndroidLogCollectorSystem(this.controller));
        }
    }
}
