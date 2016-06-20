package com.frodo.app.android.core.task;

import android.os.Process;

import com.frodo.app.framework.net.NetworkCallTask;
import com.frodo.app.framework.task.AbstractBackgroundExecutor;
import com.frodo.app.framework.task.BackgroundCallTask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created by frodo on 2015/7/6.
 */
public class AndroidBackgroundExecutorImpl extends AbstractBackgroundExecutor {

    public AndroidBackgroundExecutorImpl(ExecutorService executorService) {
        super(executorService);
    }

    @Override
    public <R> Future<R> execute(NetworkCallTask<R> task) {
        return getExecutorService().submit(new NetworkCallRunner<>(task));
    }

    @Override
    public <R> Future<R> execute(BackgroundCallTask<R> task) {
        return getExecutorService().submit(new BackgroundCallRunner<>(task));
    }

    private static class BackgroundCallRunner<R> implements Callable<R>, Comparable {
        private final BackgroundCallTask<R> mBackgroundCallTask;

        BackgroundCallRunner(BackgroundCallTask<R> task) {
            mBackgroundCallTask = task;
        }

        @Override
        public R call() throws Exception {
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

            if (mBackgroundCallTask.isCancelled()) {
                return null;
            }
            mBackgroundCallTask.preExecute();

            if (mBackgroundCallTask.isCancelled()) {
                return null;
            }
            R result = mBackgroundCallTask.runAsync();

            if (mBackgroundCallTask.isCancelled()) {
                return null;
            }
            mBackgroundCallTask.postExecute(result);
            return result;
        }

        @Override
        public int compareTo(Object another) {
            if (another instanceof BackgroundCallRunner) {
                return mBackgroundCallTask.getPriority() - ((BackgroundCallRunner) another).mBackgroundCallTask.getPriority();
            }
            return 0;
        }
    }

    private class NetworkCallRunner<R> implements Callable<R>, Comparable {

        private final NetworkCallTask<R> mNetworkCallTask;

        NetworkCallRunner(NetworkCallTask<R> task) {
            mNetworkCallTask = task;
        }

        @Override
        public R call() throws Exception {
            android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            if (mNetworkCallTask.isCancelled()) {
                return null;
            }
            mNetworkCallTask.onPreCall();

            if (mNetworkCallTask.isCancelled()) {
                return null;
            }
            R result = mNetworkCallTask.doBackgroundCall();

            if (mNetworkCallTask.isCancelled()) {
                return null;
            }
            if (result != null) {
                mNetworkCallTask.onSuccess(result);
            }

            mNetworkCallTask.onFinished();
            return result;
        }

        @Override
        public int compareTo(Object another) {
            if (another instanceof NetworkCallRunner) {
                return mNetworkCallTask.getPriority() - ((NetworkCallRunner) another).mNetworkCallTask.getPriority();
            }
            return 0;
        }
    }
}
