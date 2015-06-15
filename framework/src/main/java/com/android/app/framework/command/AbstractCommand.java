package com.android.app.framework.command;


import com.android.app.framework.controller.Notifier;
import com.google.common.base.Preconditions;

/**
 * @author frodo
 * @date 2014-11-19 00:22:50
 */
public abstract class AbstractCommand implements ICommand {
    private volatile boolean isCancel = false;
    private Notifier notifier;

    protected AbstractCommand(Notifier notifier) {
        this.notifier = Preconditions.checkNotNull(notifier, "notifier cannot be null");;
    }


    @Override
    public final boolean isCancel() {
        return isCancel;
    }

    @Override
    public final void setCancel(boolean isCancel) {
        this.isCancel = isCancel;
    }

    @Override
    public final String getName() {
        return getClass().getCanonicalName();
    }

    public final Notifier getNotifier() {
        return notifier;
    }
}
