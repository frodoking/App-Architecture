package com.frodo.app.framework.scene;

/**
 * 场景（在很多App中核心功能在不同场景下展示的方式是不一样的）
 * Created by frodo on 2015/6/15.
 */
public interface Scene {
    int onSwitch();

    int getCurrentScene();
}
