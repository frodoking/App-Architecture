package com.android.app.framework.task;

/**
 * Created by frodo on 2015/7/6.
 */
public abstract class BackgroundCallTask<R> {

    public void preExecute() {}

    public abstract R runAsync();

    public void postExecute(R result) { }
}
