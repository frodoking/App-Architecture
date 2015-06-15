package com.android.app.framework.scene;

/**
 * 用于定位整个App的场景切换(例如公共核心模块一般都会有编辑和非编辑状态的基本场景,一般性app也可以采用这种思路)
 * Created by frodo on 2015/6/15.
 */
public interface Scene {
    int onSwitch();

    int getCurrentScene();
}
