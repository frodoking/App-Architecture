package com.frodo.app.android.core.log;

import android.util.Log;

import com.frodo.app.framework.controller.AbstractChildSystem;
import com.frodo.app.framework.controller.IController;
import com.frodo.app.framework.log.LogCollector;
import com.frodo.app.framework.task.BackgroundCallTask;
import com.squareup.leakcanary.RefWatcher;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by frodo on 2015/6/20. log collector on Android
 */
public class AndroidLogCollectorSystem extends AbstractChildSystem implements LogCollector {
    private static final int LOG_ENTRY_MAX_LENGTH = 4000;
    private boolean enable;
    private String logDir;
    private int minimumLogLevel = VERBOSE;

    public AndroidLogCollectorSystem(IController controller) {
        this(controller, VERBOSE);
    }

    public AndroidLogCollectorSystem(IController controller, int minimumLogLevel) {
        super(controller);
        this.logDir = controller.getFileSystem().getFilePath() + File.separator + "log";
        this.minimumLogLevel = minimumLogLevel;
    }

    @Override
    public void enableCollect(boolean enable) {
        this.enable = enable;
    }

    @Override
    public void v(String tag, String message) {
        log(tag, VERBOSE, message, null);
    }

    @Override
    public void v(String tag, String message, Throwable t) {
        log(tag, VERBOSE, message, t);
    }

    @Override
    public void d(String tag, String message) {
        log(tag, DEBUG, message, null);
    }

    @Override
    public void d(String tag, String message, Throwable t) {
        log(tag, DEBUG, message, t);
    }

    @Override
    public void i(String tag, String message) {
        log(tag, INFO, message, null);
    }

    @Override
    public void i(String tag, String message, Throwable t) {
        log(tag, INFO, message, t);
    }

    @Override
    public void w(String tag, String message) {
        log(tag, WARN, message, null);
    }

    @Override
    public void w(String tag, String message, Throwable t) {
        log(tag, WARN, message, t);
    }

    @Override
    public void e(String tag, String message) {
        log(tag, ERROR, message, null);
    }

    @Override
    public void e(String tag, String message, Throwable t) {
        log(tag, ERROR, message, t);
    }

    @Override
    public void watchLeak(Object watchedReference) {
        RefWatcher refWatcher = AndroidLeakCanary.get().getRefWatcher();
        refWatcher.watch(watchedReference);
    }

    @Override
    public void uploadLeakBlocking(File file, String leakInfo) {
    }

    // Inspired by https://github.com/JakeWharton/timber/blob/master/timber/src/main/java/timber/log/Timber.java
    private void log(String tag, int logLevel, String message, Throwable t) {

        if (message == null || message.length() == 0) {
            if (t != null) {
                message = Log.getStackTraceString(t);
            } else {
                return; // Don't log if message is null and there is no throwable
            }
        } else if (t != null) {
            message += "\n" + Log.getStackTraceString(t);
        }

        if (getFunctionName() != null) {
            message = getFunctionName() + " - " + message;
        }

        checkWriteLog(tag, message);

        if (logLevel < minimumLogLevel) {
            return;
        }

        if (message.length() < 4000) {
            Log.println(logLevel, tag, message);
        } else {
            logMessageIgnoringLimit(logLevel, tag, message);
        }
    }

    //     Inspired by:
    //     http://stackoverflow.com/questions/8888654/android-set-max-length-of-logcat-messages
    //     https://github.com/jakubkrolewski/timber/blob/feature/logging_long_messages/timber/src/main/java/timber/log/Timber.java
    private void logMessageIgnoringLimit(int logLevel, String tag, String message) {
        while (message.length() != 0) {
            int nextNewLineIndex = message.indexOf('\n');
            int chunkLength = nextNewLineIndex != -1 ? nextNewLineIndex : message.length();
            chunkLength = Math.min(chunkLength, LOG_ENTRY_MAX_LENGTH);
            String messageChunk = message.substring(0, chunkLength);
            Log.println(logLevel, tag, messageChunk);

            if (nextNewLineIndex != -1 && nextNewLineIndex == chunkLength) {
                // Don't print out the \n twice.
                message = message.substring(chunkLength + 1);
            } else {
                message = message.substring(chunkLength);
            }
        }
    }

    private void checkWriteLog(final String tag, final String msg) {
        if (enable) {
            getController().getBackgroundExecutor().execute(new BackgroundCallTask<String>() {
                @Override
                public String runAsync() {
                    String log = getCurrentTime() + ", tag: " + tag + ", msg: " + msg;
                    getController().getFileSystem().writeToFile(logFile(), log);
                    return log;
                }

                @Override
                public String key() {
                    return "WriteLog";
                }
            });
        }
    }

    private String getToday() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddhh");
        return f.format(c.getTime());
    }

    public String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        String pattern = "yyyy/MM/dd HH:mm:ss:SSS";
        SimpleDateFormat f = new SimpleDateFormat(pattern);
        return f.format(c.getTime());
    }

    private File logFile() {
        String currentLogFile = logDir + getToday();
        return getController().getFileSystem().createFile(currentLogFile);
    }

    /**
     * Get The Current Function Name
     */
    private String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts != null) {
            for (int i = 0; i < sts.length; i++) {
                if (sts[i].getFileName().contains("Logger") && (i + 1 < sts.length)) {
                    StackTraceElement st = sts[i + 1];
                    return "[ " + Thread.currentThread().getName() + ": "
                            + st.getFileName() + ":" + st.getLineNumber() + " "
                            + st.getMethodName() + " ]";
                }
            }
        }
        return null;
    }
}
