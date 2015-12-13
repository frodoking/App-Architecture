package com.frodo.android.app.framework.controller;

import com.frodo.android.app.framework.cache.CacheSystem;
import com.frodo.android.app.framework.config.Configuration;
import com.frodo.android.app.framework.context.AppContext;
import com.frodo.android.app.framework.filesystem.FileSystem;
import com.frodo.android.app.framework.log.LogCollector;
import com.frodo.android.app.framework.net.NetworkInteractor;
import com.frodo.android.app.framework.orm.Database;
import com.frodo.android.app.framework.scene.Scene;
import com.frodo.android.app.framework.task.BackgroundExecutor;
import com.frodo.android.app.framework.theme.Theme;
import com.google.common.base.Preconditions;

/**
 * 整个app的核心
 * Created by frodo on 2015/4/1.
 */
public final class MainController implements IController {
    private BackgroundExecutor backgroundExecutor;
    private CacheSystem cacheSystem;
    private Configuration configuration;
    private AppContext appContext;
    private NetworkInteractor networkInteractor;
    private FileSystem fileSystem;
    private Database database;
    private Theme theme;
    private Scene scene;
    private LogCollector logCollector;
    private PluginManager pluginManager;
    private ModelFactory modelFactory;

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
    public AppContext getAppContext() {
        return this.appContext;
    }

    @Override
    public void setAppContext(AppContext appContext) {
        this.appContext = Preconditions.checkNotNull(appContext, "AppContext cannot be null");
    }

    @Override
    public NetworkInteractor getNetworkInteractor() {
        return this.networkInteractor;
    }

    @Override
    public void setNetworkInteractor(NetworkInteractor networkInteractor) {
        this.networkInteractor = Preconditions.checkNotNull(networkInteractor, "NetworkInteractor cannot be null");
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
    public ModelFactory getModelFactory() {
        return this.modelFactory;
    }

    @Override
    public void setModelFactory(ModelFactory modelFactory) {
        this.modelFactory = Preconditions.checkNotNull(modelFactory, "ModelFactory cannot be null");
    }

    @Override
    public PluginManager getPluginManager() {
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
}
