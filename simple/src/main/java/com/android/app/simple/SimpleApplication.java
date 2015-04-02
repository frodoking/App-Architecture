package com.android.app.simple;

import com.android.app.AppApplication;
import com.android.app.core.util.ResourceManager;
import com.android.app.framework.config.Configuration;
import com.android.app.framework.datasource.StorageSystems;

/**
 * Created by frodo on 2015/4/2.
 */
public class SimpleApplication extends AppApplication {
    private Configuration configuration = new Configuration() {
        @Override
        public String getHost() {
            return null;
        }

        @Override
        public String getVersionName() {
            return null;
        }

        @Override
        public int getVersionCode() {
            return 0;
        }

        @Override
        public boolean isDebug() {
            return ResourceManager.debugable();
        }
    };

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public StorageSystems getStorageSystems() {
        return new MovieStorageSystems();
    }
}
