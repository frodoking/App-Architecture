package com.frodo.app.framework.task;

import com.google.common.base.Preconditions;

import java.util.concurrent.ExecutorService;

/**
 * Created by frodo on 2015/7/6.
 */
public abstract class AbstractBackgroundExecutor implements BackgroundExecutor {

    private final ExecutorService executorService;

    public AbstractBackgroundExecutor(ExecutorService executorService) {
        this.executorService = Preconditions.checkNotNull(executorService, "executorService cannot be null");
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }
}
