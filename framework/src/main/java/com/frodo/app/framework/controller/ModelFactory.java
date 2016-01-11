package com.frodo.app.framework.controller;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Model工厂
 * Created by frodo on 2015/6/15.
 */
public final class ModelFactory {
    private ConcurrentMap<String, com.frodo.app.framework.controller.IModel> modelCache;

    public ModelFactory() {
        modelCache = new ConcurrentHashMap<>();
    }

    public void registerMode(com.frodo.app.framework.controller.IModel model) {
        modelCache.putIfAbsent(model.name(), model);
    }

    public com.frodo.app.framework.controller.IModel getModelBy(String modelName) {
        return modelCache.get(modelName);
    }

    public boolean removeModelBy(String modelName) {
        if (modelCache.containsKey(modelName)) {
            modelCache.remove(modelName);
            return true;
        } else {
            return false;
        }
    }

    public boolean cleanAllModel() {
        modelCache.clear();
        return true;
    }
}
