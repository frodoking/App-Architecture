package com.frodo.app.framework.task;


/**
 * Created by frodo on 2015/7/17.
 */
public abstract class CallTask implements Terminatable {
    public static final int MAX_PRIORITY = 10;
    public static final int MIN_PRIORITY = 1;
    public static final int NORM_PRIORITY = 5;

    private final TerminationToken terminationToken;
    private volatile int priority;

    public CallTask() {
        this(NORM_PRIORITY);
    }

    public CallTask(int priority) {
        this.terminationToken = new TerminationToken();
        this.priority = priority;
    }

    public final boolean isCancelled() {
        return terminationToken.isToShutdown();
    }

    public final int getPriority() {
        return this.priority;
    }

    public final void setPriority(int priority) {
        this.priority = priority;
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
