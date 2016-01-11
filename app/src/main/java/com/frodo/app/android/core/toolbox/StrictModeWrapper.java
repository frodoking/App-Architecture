package com.frodo.app.android.core.toolbox;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.StrictMode;

/**
 * Created by frodo on 2015/8/18. StrictMode
 */
public class StrictModeWrapper {
    public static void init(Context context) {
        // check if android:debuggable is set to true
        int appFlags = context.getApplicationInfo().flags;
        if ((appFlags & ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }
    }
}
