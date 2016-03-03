package com.frodo.app.android;

import android.app.Application;

import com.frodo.app.android.core.cache.AndroidCacheSystem;
import com.frodo.app.android.core.database.AndroidDatabaseSystem;
import com.frodo.app.android.core.filesystem.AndroidFileSystem;
import com.frodo.app.android.core.task.AndroidBackgroundExecutorImpl;
import com.frodo.app.android.core.task.AndroidExecutor;
import com.frodo.app.android.core.toolbox.AndroidLeakcanary;
import com.frodo.app.android.core.toolbox.ResourceManager;
import com.frodo.app.android.core.toolbox.SDCardUtils;
import com.frodo.app.android.core.toolbox.StrictModeWrapper;
import com.frodo.app.framework.broadcast.GlobalLocalBroadcastManager;
import com.frodo.app.framework.config.Configuration;
import com.frodo.app.framework.context.MicroContext;
import com.frodo.app.framework.controller.MainController;
import com.frodo.app.framework.controller.ModelFactory;
import com.frodo.app.framework.exception.ExceptionHandler;
import com.frodo.app.framework.log.LogCollector;
import com.frodo.app.framework.log.Logger;
import com.frodo.app.framework.net.NetworkTransport;
import com.frodo.app.framework.scene.Scene;
import com.frodo.app.framework.theme.Theme;

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
       /* final String imageCacheDir = getMainController().getFileSystem().getFilePath() + File.separator + "image";
        Picasso picasso = new Picasso.Builder(this).downloader(
                new OkHttpDownloader(new File(imageCacheDir))).build();
        Picasso.setSingletonInstance(picasso);*/
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
            StrictModeWrapper.enable(controller.getConfig().isDebug());
        }
    }
}
