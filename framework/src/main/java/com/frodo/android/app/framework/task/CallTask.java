package com.frodo.android.app.framework.task;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by frodo on 2015/7/17.
 */
public abstract class CallTask {
    private AtomicBoolean mayInterruptIfRunning = new AtomicBoolean(true);
    private AtomicBoolean cancel = new AtomicBoolean(false);

    public final boolean isCanCancelled() {
        return mayInterruptIfRunning.get();
    }

    public final boolean cancel(boolean mayInterruptIfRunning) {
        return this.mayInterruptIfRunning.getAndSet(mayInterruptIfRunning);
    }

    public final boolean isCancelled() {
        return cancel.get();
    }

    public final void doCancel() {
        if (!isCanCancelled()) {
            return;
        }

        if (isCancelled()) {
            return;
        }

        cancel.getAndSet(true);

        onCancel();
    }

    public void onCancel() {
    }

    /**
     * task key as for cache key
     *
     * @return String
     */
    public abstract String key();
}
