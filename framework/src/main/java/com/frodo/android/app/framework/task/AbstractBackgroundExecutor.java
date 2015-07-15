package com.frodo.android.app.framework.task;

import java.util.concurrent.ExecutorService;

import com.google.common.base.Preconditions;

/**
 * Created by frodo on 2015/7/6.
 */
public abstract class AbstractBackgroundExecutor implements BackgroundExecutor {

    private final ExecutorService mExecutorService;

    public AbstractBackgroundExecutor(ExecutorService executorService) {
        mExecutorService = Preconditions.checkNotNull(executorService, "executorService cannot be null");
    }

    public ExecutorService getExecutorService() {
        return mExecutorService;
    }
}
