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
import com.google.common.base.Preconditions;

/**
 * 整个app的核心
 * Created by frodo on 2015/4/1.
 */
public final class MainController implements com.frodo.app.framework.controller.IController {
    private BackgroundExecutor backgroundExecutor;
    private CacheSystem cacheSystem;
    private Configuration configuration;
    private MicroContext microContext;
    private LocalBroadcastManager localBroadcastManager;
    private NetworkTransport networkTransport;
    private FileSystem fileSystem;
    private Database database;
    private Theme theme;
    private Scene scene;
    private LogCollector logCollector;
    private ExceptionHandler exceptionHandler;
    private com.frodo.app.framework.controller.PluginManager pluginManager;
    private com.frodo.app.framework.controller.ModelFactory modelFactory;

    @Override
    public BackgroundExecutor getBackgroundExecutor() {
        return this.backgroundExecutor;
    }

    @Override
    public void setBackgroundExecutor(BackgroundExecutor backgroundExecutor) {
        this.backgroundExecutor = Preconditions.checkNotNull(backgroundExecutor, "BackgroundExecutor cannot be null");
    }

    @Override
    public CacheSystem getCacheSystem() {
        return this.cacheSystem;
    }

    @Override
    public void setCacheSystem(CacheSystem cacheSystem) {
        this.cacheSystem = Preconditions.checkNotNull(cacheSystem, "CacheSystem cannot be null");
    }

    @Override
    public void setConfiguration(Configuration configuration) {
        this.configuration = Preconditions.checkNotNull(configuration, "Configuration cannot be null");
    }

    @Override
    public Configuration getConfig() {
        return this.configuration;
    }

    @Override
    public MicroContext getMicroContext() {
        return this.microContext;
    }

    @Override
    public void setMicroContext(MicroContext microContext) {
        this.microContext = Preconditions.checkNotNull(microContext, "setMicroContext cannot be null");
    }

    @Override
    public LocalBroadcastManager getLocalBroadcastManager() {
        return localBroadcastManager;
    }

    @Override
    public void setLocalBroadcastManager(LocalBroadcastManager localBroadcastManager) {
        this.localBroadcastManager = Preconditions.checkNotNull(localBroadcastManager, "LocalBroadcastManager cannot be null");
    }

    @Override
    public NetworkTransport getNetworkTransport() {
        return this.networkTransport;
    }

    @Override
    public void setNetworkTransport(NetworkTransport networkTransport) {
        this.networkTransport = Preconditions.checkNotNull(networkTransport, "NetworkTransport cannot be null");
    }

    @Override
    public FileSystem getFileSystem() {
        return this.fileSystem;
    }

    @Override
    public void setFileSystem(FileSystem fileSystem) {
        this.fileSystem = Preconditions.checkNotNull(fileSystem, "FileSystem cannot be null");
    }

    @Override
    public Database getDatabase() {
        return this.database;
    }

    @Override
    public void setDatabase(Database database) {
        this.database = Preconditions.checkNotNull(database, "Database cannot be null");
    }

    @Override
    public Theme getTheme() {
        return this.theme;
    }

    @Override
    public void setTheme(Theme theme) {
        this.theme = Preconditions.checkNotNull(theme, "Theme cannot be null");
    }

    @Override
    public Scene getScene() {
        return this.scene;
    }

    @Override
    public void setScene(Scene scene) {
        this.scene = Preconditions.checkNotNull(scene, "Scene cannot be null");
    }

    @Override
    public com.frodo.app.framework.controller.ModelFactory getModelFactory() {
        return this.modelFactory;
    }

    @Override
    public void setModelFactory(ModelFactory modelFactory) {
        this.modelFactory = Preconditions.checkNotNull(modelFactory, "ModelFactory cannot be null");
    }

    @Override
    public com.frodo.app.framework.controller.PluginManager getPluginManager() {
        return pluginManager == null ? pluginManager = new PluginManager() : pluginManager;
    }

    @Override
    public LogCollector getLogCollector() {
        return this.logCollector;
    }

    @Override
    public void setLogCollector(LogCollector logCollector) {
        this.logCollector = Preconditions.checkNotNull(logCollector, "LogCollector cannot be null");
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    @Override
    public void setExceptionHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = Preconditions.checkNotNull(exceptionHandler, "ExceptionHandler cannot be null");
    }
}
