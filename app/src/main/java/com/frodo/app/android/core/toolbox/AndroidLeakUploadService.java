//package com.frodo.app.android.core.toolbox;
//
//import com.frodo.android.app.AppApplication;
//import com.frodo.android.app.framework.log.LogCollector;
//import com.squareup.leakcanary.AnalysisResult;
//import com.squareup.leakcanary.DisplayLeakService;
//import com.squareup.leakcanary.HeapDump;
//
///**
// * Created by frodo on 2015/8/18. upload service
// */
//public class AndroidLeakUploadService extends DisplayLeakService {
//
//    private LogCollector logCollector;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        final AppApplication appApplication = (AppApplication) getApplication();
//        logCollector = appApplication.getMainController().getLogCollector();
//    }
//
//    @Override
//    protected void afterDefaultHandling(HeapDump heapDump, AnalysisResult result, String leakInfo) {
//        if (!result.leakFound || result.excludedLeak) {
//            return;
//        }
//
//        if (logCollector != null)
//            logCollector.uploadLeakBlocking(heapDump.heapDumpFile, leakInfo);
//    }
//}
