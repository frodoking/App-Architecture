package com.android.app.core.cmd;

import com.android.app.framework.command.AbstractCommand;
import com.android.app.framework.controller.Notifier;

/**
 * Created by frodo on 2015/6/23.
 */
public class AndroidNetworkCommand<S> extends AbstractCommand {
    private S service;
    protected AndroidNetworkCommand(S service, Notifier notifier) {
        super(notifier);
        this.service = service;
    }

    public S getService() {
        return service;
    }

    @Override
    public void execute() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
    }
}
