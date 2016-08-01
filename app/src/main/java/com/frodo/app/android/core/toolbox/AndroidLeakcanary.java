package com.frodo.app.android.core.toolbox;

import android.app.Application;

import com.squareup.leakcanary.AndroidExcludedRefs;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by frodo on 2015/8/14. for AndroidLeakCanary
 */
public final class AndroidLeakCanary {
    private static AndroidLeakCanary instance;
    private RefWatcher refWatcher;

    public AndroidLeakCanary(RefWatcher refWatcher) {
        this.refWatcher = refWatcher;
    }

    public static AndroidLeakCanary get() {
        return instance;
    }

    public RefWatcher getRefWatcher() {
        return get().refWatcher;
    }

    public static AndroidLeakCanary newInstance(Application application) {
        if (instance != null) {
            throw new IllegalArgumentException("already exist instance");
        }
        final RefWatcher watcher = LeakCanary.install(application, AndroidLeakUploadService.class, AndroidExcludedRefs.createAppDefaults().build());
        instance = new AndroidLeakCanary(watcher);
        return instance;
    }
}
