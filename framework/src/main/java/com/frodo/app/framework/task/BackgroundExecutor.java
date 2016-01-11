package com.frodo.app.framework.task;

import com.frodo.app.framework.net.NetworkCallTask;

/**
 * Created by frodo on 2015/7/6.
 */
public interface BackgroundExecutor {
    <R> void execute(NetworkCallTask<R> runnable);

    <R> void execute(com.frodo.app.framework.task.BackgroundCallTask<R> runnable);
}
