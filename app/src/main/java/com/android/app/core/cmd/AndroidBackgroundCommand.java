package com.android.app.core.cmd;

import com.android.app.core.MainUINotifier;
import com.android.app.framework.command.AbstractCommand;

/**
 * Created by frodo on 2015/6/23.
 */
public class AndroidBackgroundCommand extends AbstractCommand {

    protected AndroidBackgroundCommand(MainUINotifier notifier) {
        super(notifier);
    }

    @Override
    public void execute() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
    }
}
