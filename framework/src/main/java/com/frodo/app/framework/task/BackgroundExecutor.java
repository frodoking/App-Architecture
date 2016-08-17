package com.frodo.app.framework.task;

import com.frodo.app.framework.net.NetworkCallTask;

import java.util.concurrent.Future;

/**
 * Created by frodo on 2015/7/6.
 */
public interface BackgroundExecutor {
    <R> Future<R> execute(NetworkCallTask<R> runnable);

    <R> Future<R> execute(BackgroundCallTask<R> runnable);
}
