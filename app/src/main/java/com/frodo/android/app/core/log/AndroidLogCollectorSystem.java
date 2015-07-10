package com.frodo.android.app.core.log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.frodo.android.app.framework.controller.AbstractChildSystem;
import com.frodo.android.app.framework.controller.IController;
import com.frodo.android.app.framework.log.LogCollector;

import android.util.Log;

/**
 * Created by frodo on 2015/6/20.
 */
public final class AndroidLogCollectorSystem extends AbstractChildSystem implements LogCollector {

    private boolean enable;
    private String logDir;

    public AndroidLogCollectorSystem(IController controller) {
        super(controller);
        logDir = controller.getFileSystem().getFilePath() + File.separator + "log";
    }

    private String getToday() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddhh");
        return f.format(c.getTime());
    }

    public String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        String pattern = "yyyy年MM月dd日 HH时mm分ss秒SSS毫秒";
        SimpleDateFormat f = new SimpleDateFormat(pattern);
        return f.format(c.getTime());
    }

    private File logFile() {
        String currentLogFile = logDir + getToday();
        try {
            getController().getFileSystem().createFile(currentLogFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File(currentLogFile);
    }

    @Override
    public void enableCollect(boolean enable) {
        this.enable = enable;
    }

    @Override
    public void logWarn(String tag, String msg) {
        checkWriteLog(tag, msg);
        if (getController().getConfig().isDebug()) {
            Log.w(tag, msg);
        }
    }

    @Override
    public void logError(String tag, String msg) {
        checkWriteLog(tag, msg);
        if (getController().getConfig().isDebug()) {
            Log.e(tag, msg);
        }
    }

    @Override
    public void logInfo(String tag, String msg) {
        checkWriteLog(tag, msg);
        if (getController().getConfig().isDebug()) {
            Log.i(tag, msg);
        }
    }

    @Override
    public boolean registerCrashHandler() {
        return false;
    }

    @Override
    public boolean unregisterCrashHandler() {
        return false;
    }

    @Override
    public boolean register(Object activity) {
        return false;
    }

    @Override
    public boolean unregister(Object activity) {
        return false;
    }

    private void checkWriteLog(String tag, String msg) {
        if (enable) {
            getController().getFileSystem().write(getCurrentTime() + ", tag: " + tag + ", msg: " + msg, logFile());
        }
    }
}
