package com.frodo.android.app;

import android.app.Application;

import com.frodo.android.app.core.cache.AndroidCacheSystem;
import com.frodo.android.app.core.database.AndroidDatabaseSystem;
import com.frodo.android.app.core.filesystem.AndroidFileSystem;
import com.frodo.android.app.core.task.AndroidBackgroundExecutorImpl;
import com.frodo.android.app.core.task.AndroidExecutor;
import com.frodo.android.app.core.toolbox.AndroidLeakcanary;
import com.frodo.android.app.core.toolbox.ResourceManager;
import com.frodo.android.app.core.toolbox.SDCardUtils;
import com.frodo.android.app.core.toolbox.StrictModeWrapper;
import com.frodo.android.app.framework.broadcast.GlobalLocalBroadcastManager;
import com.frodo.android.app.framework.config.Configuration;
import com.frodo.android.app.framework.context.MicroContext;
import com.frodo.android.app.framework.controller.MainController;
import com.frodo.android.app.framework.controller.ModelFactory;
import com.frodo.android.app.framework.exception.ExceptionHandler;
import com.frodo.android.app.framework.log.LogCollector;
import com.frodo.android.app.framework.log.Logger;
import com.frodo.android.app.framework.net.NetworkTransport;
import com.frodo.android.app.framework.scene.Scene;
import com.frodo.android.app.framework.theme.Theme;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.GINGERBREAD;

/**
 * Created by frodo on 2014/12/19. Base Application
 */
public abstract class MicroApplication extends Application implements MicroContext {
    private MainController controller;

    @Override
    public void onCreate() {
        super.onCreate();
        this.controller = new MainController();
        init();
        enabledStrictMode();
    }

    public void init() {
        final int numberCores = Runtime.getRuntime().availableProcessors();
        final AndroidExecutor executor = new AndroidExecutor("app-default",numberCores * 2 + 1);
        controller.setBackgroundExecutor(new AndroidBackgroundExecutorImpl(executor));

        controller.setMicroContext(this);
        controller.setConfiguration(loadConfiguration());
        controller.setScene(loadScene());
        controller.setTheme(loadTheme());
        controller.setFileSystem(new AndroidFileSystem(controller));
        controller.setLocalBroadcastManager(new GlobalLocalBroadcastManager());

        AndroidDatabaseSystem databaseSystem = AndroidDatabaseSystem.create(controller);
        databaseSystem.configAllowTransaction(true);
        controller.setDatabase(databaseSystem);
        controller.setNetworkTransport(loadNetworkTransport());
        controller.setModelFactory(new ModelFactory());
        final LogCollector logCollector = loadLogCollector();
        Logger.LOGCOLLECTOR = logCollector;
        controller.setLogCollector(logCollector);
        controller.setExceptionHandler(loadExceptionHandler());

        enableCache(true);

        ResourceManager.newInstance(this);

        AndroidLeakcanary.newInstance(this);

        loadServerConfiguration();
    }

    public final MainController getMainController() {
        return this.controller;
    }

    public abstract LogCollector loadLogCollector();

    public abstract Configuration loadConfiguration();

    public abstract Scene loadScene();

    public abstract Theme loadTheme();

    public abstract NetworkTransport loadNetworkTransport();

    public abstract ExceptionHandler loadExceptionHandler();

    public final void enableCache(boolean enable) {
        if (enable) {
            controller.setCacheSystem(new AndroidCacheSystem(this.controller, "/"));
            loadImageCache();
        } else {
            controller.setCacheSystem(null);
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

    private void enabledStrictMode() {
        if (SDK_INT >= GINGERBREAD) {
            StrictModeWrapper.init(this);
        }
    }
}
