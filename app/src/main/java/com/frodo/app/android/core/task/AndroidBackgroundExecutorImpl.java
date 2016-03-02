package com.frodo.app.android.core.task;

import android.os.Process;

import com.frodo.app.framework.exception.HttpException;
import com.frodo.app.framework.net.NetworkCallTask;
import com.frodo.app.framework.task.AbstractBackgroundExecutor;
import com.frodo.app.framework.task.BackgroundCallTask;

import java.util.concurrent.ExecutorService;

/**
 * Created by frodo on 2015/7/6.
 */
public class AndroidBackgroundExecutorImpl extends AbstractBackgroundExecutor {

    public AndroidBackgroundExecutorImpl(ExecutorService executorService) {
        super(executorService);
    }

    @Override
    public <R> void execute(NetworkCallTask<R> runnable) {
        getExecutorService().execute(new NetworkCallRunner<>(runnable));
    }

    @Override
    public <R> void execute(BackgroundCallTask<R> runnable) {
        getExecutorService().execute(new BackgroundCallRunner<>(runnable));
    }

    private class BackgroundCallRunner<R> implements Runnable {
        private final BackgroundCallTask<R> mBackgroundCallTask;

        BackgroundCallRunner(BackgroundCallTask<R> task) {
            mBackgroundCallTask = task;
        }

        @Override
        public final void run() {
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

            if (mBackgroundCallTask.isCancelled()) {
                return;
            }
            mBackgroundCallTask.preExecute();

            if (mBackgroundCallTask.isCancelled()) {
                return;
            }
            R result = mBackgroundCallTask.runAsync();

            if (mBackgroundCallTask.isCancelled()) {
                return;
            }
            mBackgroundCallTask.postExecute(result);
        }
    }

    private class NetworkCallRunner<R> implements Runnable {

        private final NetworkCallTask<R> mNetworkCallTask;

        NetworkCallRunner(NetworkCallTask<R> task) {
            mNetworkCallTask = task;
        }

        @Override
        public final void run() {
            android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            if (mNetworkCallTask.isCancelled()) {
                return;
            }
            mNetworkCallTask.onPreCall();

            R result = null;
            HttpException httpException = null;

            try {
                if (mNetworkCallTask.isCancelled()) {
                    return;
                }
                result = mNetworkCallTask.doBackgroundCall();
            } catch (HttpException re) {
                httpException = re;
            }

            if (mNetworkCallTask.isCancelled()) {
                return;
            }

            if (result != null) {
                mNetworkCallTask.onSuccess(result);
            } else if (httpException != null) {
                mNetworkCallTask.onError(httpException);
            }
            mNetworkCallTask.onFinished();
        }
    }
}
