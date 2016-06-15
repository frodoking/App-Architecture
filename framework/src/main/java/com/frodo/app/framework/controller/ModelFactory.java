package com.frodo.app.framework.controller;

import com.frodo.app.framework.log.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Model工厂
 * Created by frodo on 2015/6/15.
 */
public final class ModelFactory {
    private final ConcurrentMap<String, IModel> modelCache;

    public ModelFactory() {
        modelCache = new ConcurrentHashMap<>();
    }

    public void registerMode(IModel model) {
        modelCache.putIfAbsent(model.name(), model);
    }

    public boolean containModelBy(String key) {
        return modelCache.containsKey(key);
    }

    public IModel getModelBy(String modelName) {
        return modelCache.get(modelName);
    }

    @SuppressWarnings("unchecked")
    public <M extends IModel> M getOrCreateIfAbsent(String key, Class<M> modelClassName, MainController mainController) {
        if (containModelBy(key)) {
            IModel model = getModelBy(key);
            if (model == null) {
                throw new IllegalArgumentException("error modelClassName");
            } else {
                return (M) model;
            }
        } else {
            try {
                Constructor<M> constructor = modelClassName.getDeclaredConstructor(MainController.class);
                if (constructor != null) {
                    Logger.fLog().tag("ModelFactory").i(String.format("create model [%s]", modelClassName));
                    return constructor.newInstance(mainController);
                }
            } catch (InstantiationException e) {
                Logger.fLog().tag("ModelFactory").e("getOrCreateIfAbsent", e);
            } catch (IllegalAccessException e) {
                Logger.fLog().tag("ModelFactory").e("getOrCreateIfAbsent", e);
            } catch (InvocationTargetException e) {
                Logger.fLog().tag("ModelFactory").e("getOrCreateIfAbsent", e);
            } catch (NoSuchMethodException e) {
                Logger.fLog().tag("ModelFactory").e("getOrCreateIfAbsent", e);
            }
            return (M) getOrCreateIfAbsent(IModel.MODEL_DEFAULT, AbstractModel.SimpleModel.class, mainController);
        }
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
