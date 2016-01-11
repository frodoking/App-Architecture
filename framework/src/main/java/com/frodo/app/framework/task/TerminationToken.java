package com.frodo.app.framework.task;

/**
 * Created by frodo on 2015/8/18.
 */
public class TerminationToken {
    //使用volatile修饰，以保证无需显示锁的情况下该变量的内存可见性
    protected volatile boolean toShutdown = false;

    public boolean isToShutdown() {
        return toShutdown;
    }

    protected void setToShutdown(boolean toShutdown) {
        this.toShutdown = true;
    }
}
