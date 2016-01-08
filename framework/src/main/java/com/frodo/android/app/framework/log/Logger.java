package com.frodo.android.app.framework.log;

import java.util.Hashtable;

/**
 * Created by frodo on 2015/9/14. static logger
 */
public class Logger {
    public static LogCollector LOGCOLLECTOR = null;
    private static Hashtable<String, Logger> loggerTable = new Hashtable<>();

    private LogCollector logCollector;
    private String tag;
    private String user;

    public static Logger fLog() {
        return uLog("frodo");
    }

    public static Logger uLog(String user) {
        if (loggerTable.contains(user)) {
            return loggerTable.get(user);
        } else {
            Logger logger = new Logger(LOGCOLLECTOR);
            logger.user = user;
            loggerTable.put(user, logger);
            return logger;
        }
    }

    public Logger tag(String tag) {
        this.tag = tag;
        return this;
    }

    public Logger(LogCollector logCollector) {
        this.logCollector = logCollector;
    }

    public void v(String message) {
        logCollector.v(tag, wrapUser(message));
    }

    public void v(String message, Throwable t) {
        logCollector.v(tag, wrapUser(message), t);
    }

    public void d(String message) {
        logCollector.d(tag, wrapUser(message));
    }

    public void d(String message, Throwable t) {
        logCollector.d(tag, wrapUser(message), t);
    }

    public void i(String message) {
        logCollector.i(tag, wrapUser(message));
    }

    public void i(String message, Throwable t) {
        logCollector.i(tag, wrapUser(message), t);
    }

    public void w(String message) {
        logCollector.w(tag, wrapUser(message));
    }

    public void w(String message, Throwable t) {
        logCollector.w(tag, wrapUser(message), t);
    }

    public void e(String message) {
        logCollector.e(tag, wrapUser(message));
    }

    public void e(String message, Throwable t) {
        logCollector.e(tag, wrapUser(message), t);
    }

    private String wrapUser(String message) {
        return "@" + user + "@ " + message;
    }
}
