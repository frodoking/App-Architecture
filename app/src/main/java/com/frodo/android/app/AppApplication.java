package com.frodo.android.app;

import android.app.Application;

import com.frodo.android.app.core.cache.AndroidCacheSystem;
import com.frodo.android.app.core.filesystem.AndroidFileSystem;
import com.frodo.android.app.core.log.AndroidLogCollectorSystem;
import com.frodo.android.app.core.task.AndroidBackgroundExecutorImpl;
import com.frodo.android.app.core.task.AndroidExecutor;
import com.frodo.android.app.core.toolbox.ResourceManager;
import com.frodo.android.app.core.toolbox.SDCardUtils;
import com.frodo.android.app.framework.config.Configuration;
import com.frodo.android.app.framework.context.Context;
import com.frodo.android.app.framework.controller.MainController;
import com.frodo.android.app.framework.controller.ModelFactory;
import com.frodo.android.app.framework.net.NetworkInteractor;
import com.frodo.android.app.framework.scene.Scene;
import com.frodo.android.app.framework.theme.Theme;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by frodo on 2014/12/19. Base Application
 */
public abstract class AppApplication extends Application implements Context {
    private MainController controller;

    @Override
    public void onCreate() {
        super.onCreate();
        this.controller = new MainController();
        init();
    }

    public void init() {
        final int numberCores = Runtime.getRuntime().availableProcessors();
        final AndroidExecutor executor = new AndroidExecutor("app-default",numberCores * 2 + 1);
        controller.setBackgroundExecutor(new AndroidBackgroundExecutorImpl(executor));

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

        loadServerConfiguration();
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
            controller.setCacheSystem(new AndroidCacheSystem(this.controller, "/"));
            loadImageCache();
        } else {
            controller.setCacheSystem(null);
        }
    }

    public final void enableLogCollector(boolean enable) {
        if (enable) {
            controller.setLogCollector(new AndroidLogCollectorSystem(this.controller));
        } else {
            controller.setLogCollector(null);
        }
    }

    public abstract void loadServerConfiguration();

    private void loadImageCache() {
        final String imageCacheDir = getMainController().getFileSystem().getFilePath() + File.separator + "image";
        Picasso picasso = new Picasso.Builder(this).downloader(
                new OkHttpDownloader(new File(imageCacheDir))).build();
        Picasso.setSingletonInstance(picasso);
    }

    @Override
    public String getRootDirName() {
        return SDCardUtils.getSDCardPath() + File.separator;
    }

    @Override
    public String getFilesDirName() {
        return getRootDirName() + getPackageName() + File.separator;
    }
}
