package com.frodo.app.framework.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by frodo on 2015/9/1. 插件化管理类
 */
public class PluginManager {
    private Map<String, PluginChildSystem> pluginChildSystemMap;

    public PluginManager() {
        pluginChildSystemMap = new HashMap<>();
    }

    public PluginChildSystem getPluginChildSystem(String tag) {
        return pluginChildSystemMap.get(tag);
    }

    public void loadPluginChildSystem(PluginChildSystem pluginChildSystem) {
        pluginChildSystemMap.put(pluginChildSystem.systemName(), pluginChildSystem);
    }

    public void unloadPluginChildSystem(PluginChildSystem pluginChildSystem) {
        pluginChildSystemMap.remove(pluginChildSystem.systemName());
    }
}
