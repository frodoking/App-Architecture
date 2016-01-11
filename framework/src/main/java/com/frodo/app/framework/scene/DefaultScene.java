package com.frodo.app.framework.scene;

/**
 * 默认场景
 * Created by frodo on 2015/6/15.
 */
public class DefaultScene implements com.frodo.app.framework.scene.Scene {
    @Override
    public int onSwitch() {
        return 0;
    }

    @Override
    public int getCurrentScene() {
        return 0;
    }
}
