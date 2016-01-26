package com.frodo.app.framework.controller;

import com.frodo.app.framework.broadcast.LocalBroadcastManager;
import com.frodo.app.framework.cache.CacheSystem;
import com.frodo.app.framework.config.Configuration;
import com.frodo.app.framework.context.MicroContext;
import com.frodo.app.framework.exception.ExceptionHandler;
import com.frodo.app.framework.filesystem.FileSystem;
import com.frodo.app.framework.log.LogCollector;
import com.frodo.app.framework.net.NetworkTransport;
import com.frodo.app.framework.orm.Database;
import com.frodo.app.framework.scene.Scene;
import com.frodo.app.framework.task.BackgroundExecutor;
import com.frodo.app.framework.theme.Theme;

/**
 * controller接口
 * <p>
 * Fixed by frodo on 2015/6/20.
 */
public interface IController {

    BackgroundExecutor getBackgroundExecutor();

    void setBackgroundExecutor(BackgroundExecutor backgroundExecutor);

    CacheSystem getCacheSystem();

    void setCacheSystem(CacheSystem cache);

    void setConfiguration(Configuration configuration);

    Configuration getConfig();

    MicroContext getMicroContext();

    void setMicroContext(MicroContext context);

    LocalBroadcastManager getLocalBroadcastManager();

    void setLocalBroadcastManager(LocalBroadcastManager localBroadcastManager);

    NetworkTransport getNetworkTransport();

    void setNetworkTransport(NetworkTransport networkTransport);

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

    PluginManager getPluginManager();

    LogCollector getLogCollector();

    void setLogCollector(LogCollector logCollector);

    ExceptionHandler getExceptionHandler();

    void setExceptionHandler(ExceptionHandler exceptionHandler);
}
