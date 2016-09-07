package com.frodo.app.android;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.frodo.app.android.core.cache.AndroidCacheSystem;
import com.frodo.app.android.core.database.AndroidDatabaseSystem;
import com.frodo.app.android.core.exception.AndroidCrashHandler;
import com.frodo.app.android.core.filesystem.AndroidFileSystem;
import com.frodo.app.android.core.log.AndroidLogCollectorSystem;
import com.frodo.app.android.core.task.AndroidBackgroundExecutorImpl;
import com.frodo.app.android.core.task.AndroidExecutor;
import com.frodo.app.android.core.toolbox.ResourceManager;
import com.frodo.app.android.core.toolbox.SDCardUtils;
import com.frodo.app.android.core.toolbox.StrictModeWrapper;
import com.frodo.app.android.ui.FragmentScheduler;
import com.frodo.app.framework.broadcast.GlobalLocalBroadcastManager;
import com.frodo.app.framework.config.Configuration;
import com.frodo.app.framework.context.MicroContext;
import com.frodo.app.framework.controller.MainController;
import com.frodo.app.framework.controller.ModelFactory;
import com.frodo.app.framework.exception.ExceptionHandler;
import com.frodo.app.framework.log.LogCollector;
import com.frodo.app.framework.log.Logger;
import com.frodo.app.framework.net.NetworkTransport;
import com.frodo.app.framework.scene.DefaultScene;
import com.frodo.app.framework.scene.Scene;
import com.frodo.app.framework.theme.Theme;

import java.io.File;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.GINGERBREAD;

/**
 * Created by frodo on 2014/12/19. Base Application (use composite, don't to extend {@link Application} Or {@link MultiApplication})
 */
public abstract class MicroContextLoader implements MicroContext<Context> {

    private Application application;
    private MainController controller;

    public MicroContextLoader(Application application) {
        this.application = application;
        this.controller = new MainController();
        initialize();
        enabledStrictMode();
        Fresco.initialize(application);
    }

    private void initialize() {
        final int numberCores = Runtime.getRuntime().availableProcessors();
        final AndroidExecutor executor = new AndroidExecutor("app-default", numberCores * 2 + 1);
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

        FragmentScheduler.findDirectScheme(application);

        ResourceManager.newInstance(application);

        loadServerConfiguration();
    }

    @Override
    public Application getContext() {
        return application;
    }

    public final MainController getMainController() {
        return this.controller;
    }

    public LogCollector loadLogCollector() {
        return new AndroidLogCollectorSystem(getMainController());
    }

    public abstract Configuration loadConfiguration();

    public Scene loadScene() {
        return new DefaultScene();
    }

    public Theme loadTheme() {
        return new Theme() {
            @Override
            public int themeColor() {
                return 0xff00fe;
            }
        };
    }

    public abstract NetworkTransport loadNetworkTransport();

    public ExceptionHandler loadExceptionHandler() {
        return new AndroidCrashHandler(getMainController());
    }

    public final void enableCache(boolean enable) {
        if (enable) {
            controller.setCacheSystem(new AndroidCacheSystem(this.controller, getFilesDirName() + "cache" + File.separator));
        } else {
            controller.setCacheSystem(null);
        }
    }

    public void loadServerConfiguration() {
        // do something
    }

    @Override
    public String getRootDirName() {
        return SDCardUtils.getSDCardPath() + File.separator;
    }

    @Override
    public String getFilesDirName() {
        return getRootDirName() + application.getPackageName() + File.separator;
    }

    @Override
    public String applicationName() {
        return ResourceManager.getPackageInfo().packageName;
    }

    @Override
    public int versionCode() {
        return ResourceManager.getPackageInfo().versionCode;
    }

    @Override
    public String versionName() {
        return ResourceManager.getPackageInfo().versionName;
    }

    private void enabledStrictMode() {
        if (SDK_INT >= GINGERBREAD) {
            StrictModeWrapper.enable(controller.getConfig().isDebug());
        }
    }
}
