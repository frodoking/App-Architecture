package com.frodo.android.app.core.toolbox;

import android.app.Application;

import com.frodo.android.app.AppApplication;
import com.frodo.android.app.framework.log.LogCollector;
import com.squareup.leakcanary.AnalysisResult;
import com.squareup.leakcanary.DisplayLeakService;
import com.squareup.leakcanary.ExcludedRefs;
import com.squareup.leakcanary.HeapDump;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by frodo on 2015/8/14. for AndroidLeakcanary
 */
public class AndroidLeakcanary {
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

        final ExcludedRefs excludedRefs = new ExcludedRefs.Builder().build();
        final RefWatcher watcher = LeakCanary.install(application, LeakUploadService.class, excludedRefs);
        instance = new AndroidLeakcanary(watcher);
        return instance;
    }

    public static class LeakUploadService extends DisplayLeakService {

        private LogCollector logCollector;

        @Override
        public void onCreate() {
            super.onCreate();

            final AppApplication appApplication = (AppApplication) getApplication();
            logCollector = appApplication.getMainController().getLogCollector();
        }

        @Override
        protected void afterDefaultHandling(HeapDump heapDump, AnalysisResult result, String leakInfo) {
            if (!result.leakFound || result.excludedLeak) {
                return;
            }

            if (logCollector != null)
                logCollector.uploadLeakBlocking(heapDump.heapDumpFile, leakInfo);
        }
    }
}
