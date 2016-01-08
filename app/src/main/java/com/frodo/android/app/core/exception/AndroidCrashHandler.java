package com.frodo.android.app.core.exception;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import com.frodo.android.app.framework.controller.MainController;
import com.frodo.android.app.framework.exception.AbstractExceptionHandler;
import com.frodo.android.app.framework.log.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by frodo on 2016/1/7. 异常处理Handler
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
        return getController().getCacheSystem().getCacheDir() + File.separator + CRASH_LOG_DIR;
    }

    private void dumpException(Throwable ex, String path) {
        // 如果SD卡不存在或无法使用，则无法把异常信息写入SD卡
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (getController().getConfig().isDebug()) {
                Logger.fLog().tag(getClass().getSimpleName()).d("sdcard unmounted,skip dump exception");
                return;
            }
        }

        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date(current));
        //以当前时间创建log文件
        File file = new File(path + time + ".log");

        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            // 导出发生异常的时间
            pw.println(time);

            // 导出手机信息
            dumpPhoneInfo(pw);

            pw.println();
            // 导出异常的调用栈信息
            ex.printStackTrace(pw);

            pw.close();
        } catch (Exception e) {
            Logger.fLog().tag(getClass().getSimpleName()).e("dump crash info failed", e);
        }
    }

    private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        //应用的版本名称和版本号
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        pw.print("App Version: ");
        pw.print(pi.versionName);
        pw.print('_');
        pw.println(pi.versionCode);

        //android版本号
        pw.print("OS Version: ");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.println(Build.VERSION.SDK_INT);

        //手机制造商
        pw.print("Vendor: ");
        pw.println(Build.MANUFACTURER);

        //手机型号
        pw.print("Model: ");
        pw.println(Build.MODEL);

        //cpu架构
        pw.print("CPU ABI: ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pw.println(Build.SUPPORTED_ABIS);
        }else {
            pw.println(Build.CPU_ABI);
        }
    }

    public void uploadExceptionToServer(Throwable e) {

    }
}
