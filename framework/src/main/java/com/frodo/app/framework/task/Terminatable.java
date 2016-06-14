package com.frodo.app.framework.task;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by frodo on 2015/8/18.
 */
public interface Terminatable {
    void terminate();

    /**
     * Termination Token
     */
    final class TerminationToken {
        private AtomicBoolean toShutdown = new AtomicBoolean(false);

        boolean isToShutdown() {
            return toShutdown.get();
        }

        void setToShutdown(boolean toShutdown) {
            this.toShutdown.set(toShutdown);
        }
    }
}
