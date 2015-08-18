package com.frodo.android.app.core.toolbox;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by frodo on 2015/8/14. for AndroidLeakcanary
 */
public final class AndroidLeakcanary {
    private static AndroidLeakcanary instance;
    private RefWatcher refWatcher;

    public AndroidLeakcanary(RefWatcher refWatcher) {
        this.refWatcher = refWatcher;
    }

    public static AndroidLeakcanary get() {
        return instance;
    }

    public RefWatcher getRefWatcher() {
        return get().refWatcher;
    }

    public static AndroidLeakcanary newInstance(Application application) {
        if (instance != null) {
            throw new IllegalArgumentException("already exist instance");
        }
        final RefWatcher watcher = LeakCanary.install(application);
        instance = new AndroidLeakcanary(watcher);
        return instance;
    }
}
