package com.android.app.framework.controller;

import com.android.app.framework.cache.Cache;
import com.android.app.framework.config.Configuration;
import com.android.app.framework.context.Context;
import com.android.app.framework.filesystem.FileSystem;
import com.android.app.framework.log.LogCollector;
import com.android.app.framework.net.NetworkInteractor;
import com.android.app.framework.orm.Database;
import com.android.app.framework.scene.Scene;
import com.android.app.framework.task.BackgroundExecutor;
import com.android.app.framework.theme.Theme;

/**
 * controller接口
 *
 * @author: frodoking
 * @date: 2014-11-28 fixed 2015.6.20
 */
public interface IController {

    BackgroundExecutor getBackgroundExecutor();

    void setBackgroundExecutor(BackgroundExecutor backgroundExecutor);

    Cache getCache();

    void setCache(Cache cache);

    void setConfiguration(Configuration configuration);

    Configuration getConfig();

    Context getContext();

    void setContext(Context context);

    NetworkInteractor getNetworkInteractor();

    void setNetworkInteractor(NetworkInteractor networkInteractor);

    FileSystem getFileSystem();

    void setFileSystem(FileSystem fileSystem);

    Database getDatabase();

    void setDatabase(Database database);

    Theme getTheme();

    void setTheme(Theme theme);

    Scene getScene();

    void setScene(Scene scene);

    ModelFactory getModelFactory();

    void setModelFactory(ModelFactory modelFactory);

    LogCollector getLogCollector();

    void setLogCollector(LogCollector logCollector);
}
