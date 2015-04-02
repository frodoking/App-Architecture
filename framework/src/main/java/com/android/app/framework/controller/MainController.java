package com.android.app.framework.controller;

import com.android.app.framework.config.Configuration;
import com.android.app.framework.datasource.StorageSystems;
import com.google.common.base.Preconditions;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 整个app的核心
 * Created by frodo on 2015/4/1.
 */
public class MainController implements IController {

    private Configuration configuration;
    private StorageSystems storageSystems;
    private ConcurrentMap<String, IModel> modelCache;

    public MainController(Configuration configuration, StorageSystems storageSystems) {
        this.configuration = Preconditions.checkNotNull(configuration, "configuration cannot be null");
        this.storageSystems = Preconditions.checkNotNull(storageSystems, "storageSystems cannot be null");
        modelCache = new ConcurrentHashMap<>();
    }

    public final void registerMode(IModel model) {
        modelCache.putIfAbsent(model.name(), model);
    }

    public final void removeMode(String modelName) {
        modelCache.remove(modelName);
    }

    public final void clean() {
        modelCache.clear();
    }

    public final IModel findModelByName(String modelName){
        return modelCache.get(modelName);
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public StorageSystems getStorageSystems() {
        return storageSystems;
    }
}
