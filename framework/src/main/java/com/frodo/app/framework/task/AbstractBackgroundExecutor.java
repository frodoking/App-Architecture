package com.frodo.app.framework.task;

import com.google.common.base.Preconditions;

import java.util.concurrent.ExecutorService;

/**
 * Created by frodo on 2015/7/6.
 */
public abstract class AbstractBackgroundExecutor implements com.frodo.app.framework.task.BackgroundExecutor {

    private final ExecutorService mExecutorService;

    public AbstractBackgroundExecutor(ExecutorService executorService) {
        mExecutorService = Preconditions.checkNotNull(executorService, "executorService cannot be null");
    }

    public ExecutorService getExecutorService() {
        return mExecutorService;
    }
}
