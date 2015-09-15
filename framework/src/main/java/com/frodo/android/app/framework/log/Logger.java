package com.frodo.android.app.framework.log;

/**
 * Created by frodo on 2015/9/14. static logger
 */
public class Logger {
    public static Logger instance = null;
    private LogCollector logCollector;
    private String tag;

    public static Logger tag(String tag) {
        Logger.instance.tag = tag;
        return Logger.instance;
    }

    public Logger(LogCollector logCollector) {
        this.logCollector = logCollector;
    }

    public void printLifeCycle(String methodName) {
        logCollector.d(tag, " life cycle >> ==== > " + methodName + " < ==== <<");
    }

    public void printLog(String log) {
        logCollector.i(tag, " >> ---- > " + log + " < ---- <<");
    }
}
