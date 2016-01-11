package com.frodo.app.framework.task;

/**
 * Created by frodo on 2015/7/17.
 */
public abstract class CallTask implements Terminatable {
    private final TerminationToken terminationToken;

    public CallTask() {
        this.terminationToken = new TerminationToken();
    }

    public final boolean isCancelled() {
        return terminationToken.isToShutdown();
    }

    @Override
    public void terminate() {
        terminationToken.setToShutdown(true);
    }

    /**
     * task key as for cache key
     *
     * @return String
     */
    public abstract String key();
}
