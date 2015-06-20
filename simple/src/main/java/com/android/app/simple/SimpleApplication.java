package com.android.app.simple;

import com.android.app.AppApplication;
import com.android.app.core.config.AndroidConfig;
import com.android.app.core.network.AndroidNetworkSystem;
import com.android.app.framework.config.Configuration;
import com.android.app.framework.net.NetworkInteractor;
import com.android.app.framework.scene.DefaultScene;
import com.android.app.framework.scene.Scene;
import com.android.app.framework.theme.Theme;

/**
 * Created by frodo on 2015/4/2.
 */
public class SimpleApplication extends AppApplication {

    @Override
    public Configuration loadConfiguration() {
        return new AndroidConfig(getMainController());
    }

    @Override
    public Scene loadScene() {
        return new DefaultScene();
    }

    @Override
    public Theme loadTheme() {
        return new Theme() {
            @Override
            public int themeColor() {
                return 0xff00fe;
            }
        };
    }

    @Override
    public NetworkInteractor loadNetworkInteractor() {
        return new AndroidNetworkSystem(getMainController());
    }
}
