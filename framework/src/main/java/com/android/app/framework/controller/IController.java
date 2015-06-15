package com.android.app.framework.controller;

import com.android.app.framework.config.Configuration;
import com.android.app.framework.datasource.StorageSystems;
import com.android.app.framework.scene.Scene;
import com.android.app.framework.theme.Theme;

/**
 * controller接口
 *
 * @author: frodoking
 * @date: 2014-11-28
 */
public interface IController {
    Configuration getConfig();

    Theme getTheme();

    Scene getScene();

    StorageSystems getStorageSystems();

    ModelFactory getModelFactory();
}
