package com.frodo.app.android.core.exception;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import com.frodo.app.framework.controller.MainController;
import com.frodo.app.framework.exception.AbstractExceptionHandler;
import com.frodo.app.framework.log.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by frodo on 2016/1/7. Android crash handler
 */
public class AndroidCrashHandler extends AbstractExceptionHandler {

    private Context mContext;

    public AndroidCrashHandler(MainController controller) {
        super(controller);
        mContext = (Context) controller.getMicroContext();
    }

    @Override
    public void handle(Throwable e) {
        dumpException(e, getExceptionDir());
        uploadExceptionToServer(e);
    }

    public String getExceptionDir() {
        return getController().getFileSystem().getFilePath() + File.separator + CRASH_LOG_DIR;
    }

    private void dumpException(Throwable ex, String path) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && getController().getConfig().isDebug()) {
            Logger.fLog().tag(getClass().getSimpleName()).d("sdcard unmounted,skip dump exception");
            return;
        }

        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date(current));
        File file = new File(path + time + ".log");

        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            pw.println(time);
            dumpPhoneInfo(pw);

            pw.println();
            ex.printStackTrace(pw);

            pw.close();
        } catch (Exception e) {
            Logger.fLog().tag(getClass().getSimpleName()).e("dump crash info failed", e);
        }
    }

    private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        pw.print("App Version: ");
        pw.print(pi.versionName);
        pw.print('_');
        pw.println(pi.versionCode);

        pw.print("OS Version: ");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.println(Build.VERSION.SDK_INT);

        pw.print("Vendor: ");
        pw.println(Build.MANUFACTURER);

        pw.print("Model: ");
        pw.println(Build.MODEL);

        pw.print("CPU ABI: ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pw.println(Build.SUPPORTED_ABIS);
        } else {
            pw.println(Build.CPU_ABI);
        }
    }

    public void uploadExceptionToServer(Throwable e) {

    }
}
