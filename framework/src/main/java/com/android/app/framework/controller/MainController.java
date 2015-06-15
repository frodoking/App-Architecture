package com.android.app.framework.controller;

import com.android.app.framework.config.Configuration;
import com.android.app.framework.datasource.StorageSystems;
import com.android.app.framework.scene.Scene;
import com.android.app.framework.theme.Theme;
import com.google.common.base.Preconditions;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 整个app的核心
 * Created by frodo on 2015/4/1.
 */
public class MainController implements IController {
    private Configuration configuration;
    private Scene scene;
    private Theme theme;
    private StorageSystems storageSystems;
    private ModelFactory modelFactory;

    public MainController(Configuration configuration,
                          Scene scene,
                          Theme theme,
                          StorageSystems storageSystems) {
        this.configuration = Preconditions.checkNotNull(configuration, "configuration cannot be null");
        this.scene = Preconditions.checkNotNull(scene, "scene cannot be null");
        this.theme = Preconditions.checkNotNull(theme, "theme cannot be null");
        this.storageSystems = Preconditions.checkNotNull(storageSystems, "storageSystems cannot be null");
        modelFactory = new ModelFactory();
    }

    @Override
    public final Configuration getConfig() {
        return configuration;
    }

    @Override
    public final Theme getTheme() {
        return theme;
    }

    @Override
    public final ModelFactory getModelFactory() {
        return modelFactory;
    }

    @Override
    public final Scene getScene() {
        return scene;
    }

    @Override
    public final StorageSystems getStorageSystems() {
        return storageSystems;
    }
}
