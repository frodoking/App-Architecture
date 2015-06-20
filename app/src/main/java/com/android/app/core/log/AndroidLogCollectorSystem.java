package com.android.app.core.log;

import com.android.app.framework.controller.AbstractChildSystem;
import com.android.app.framework.controller.IController;
import com.android.app.framework.log.LogCollector;

/**
 * Created by frodo on 2015/6/20.
 */
public class AndroidLogCollectorSystem extends AbstractChildSystem implements LogCollector {

    public AndroidLogCollectorSystem(IController controller) {
        super(controller);
    }

    @Override
    public boolean log(String tag, String msg, int logType) {
        return false;
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
}
