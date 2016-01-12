package com.frodo.app.framework.task;

/**
 * Created by frodo on 2015/8/18. Termination Token
 */
public class TerminationToken {
    protected volatile boolean toShutdown = false;

    public boolean isToShutdown() {
        return toShutdown;
    }

    protected void setToShutdown(boolean toShutdown) {
        this.toShutdown = true;
    }
}
