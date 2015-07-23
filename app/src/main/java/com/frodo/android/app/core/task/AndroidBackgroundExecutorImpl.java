package com.frodo.android.app.core.task;

import java.util.concurrent.ExecutorService;

import com.frodo.android.app.framework.net.NetworkCallTask;
import com.frodo.android.app.framework.task.AbstractBackgroundExecutor;
import com.frodo.android.app.framework.task.BackgroundCallTask;
import com.frodo.android.app.framework.task.CallTask;

import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import retrofit.RetrofitError;

/**
 * Created by frodo on 2015/7/6.
 */
public class AndroidBackgroundExecutorImpl extends AbstractBackgroundExecutor {

    private static final Handler sHandler = new Handler(Looper.getMainLooper());

    public AndroidBackgroundExecutorImpl(ExecutorService executorService) {
        super(executorService);
    }

    private static boolean checkCancel(CallTask task) {
        return task.isCanCancelled() && task.isCancelled();
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
            if (checkCancel(mBackgroundCallTask)) {
                return;
            }
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

            if (checkCancel(mBackgroundCallTask)) {
                return;
            }
            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (checkCancel(mBackgroundCallTask)) {
                        return;
                    }
                    mBackgroundCallTask.preExecute();
                }
            });

            if (checkCancel(mBackgroundCallTask)) {
                return;
            }
            R result = mBackgroundCallTask.runAsync();

            if (checkCancel(mBackgroundCallTask)) {
                return;
            }
            sHandler.post(new ResultCallback(result));
        }

        private class ResultCallback implements Runnable {
            private final R mResult;

            private ResultCallback(R result) {
                mResult = result;
            }

            @Override
            public void run() {
                if (checkCancel(mBackgroundCallTask)) {
                    return;
                }
                mBackgroundCallTask.postExecute(mResult);
            }
        }
    }

    private class NetworkCallRunner<R> implements Runnable {

        private final NetworkCallTask<R> mNetworkCallTask;

        NetworkCallRunner(NetworkCallTask<R> task) {
            mNetworkCallTask = task;
        }

        @Override
        public final void run() {
            if (checkCancel(mNetworkCallTask)) {
                return;
            }
            android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (checkCancel(mNetworkCallTask)) {
                        return;
                    }
                    mNetworkCallTask.onPreCall();
                }
            });

            R result = null;
            RetrofitError retrofitError = null;

            try {
                if (checkCancel(mNetworkCallTask)) {
                    return;
                }
                result = mNetworkCallTask.doBackgroundCall();
            } catch (RetrofitError re) {
                retrofitError = re;
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (checkCancel(mNetworkCallTask)) {
                return;
            }
            sHandler.post(new ResultCallback(result, retrofitError));
        }

        private class ResultCallback implements Runnable {
            private final R mResult;
            private final RetrofitError mRetrofitError;

            private ResultCallback(R result, RetrofitError retrofitError) {
                mResult = result;
                mRetrofitError = retrofitError;
            }

            @Override
            public void run() {
                if (checkCancel(mNetworkCallTask)) {
                    return;
                }
                if (mResult != null) {
                    mNetworkCallTask.onSuccess(mResult);
                } else if (mRetrofitError != null) {
                    mNetworkCallTask.onError(mRetrofitError);
                }
                mNetworkCallTask.onFinished();
            }
        }
    }
}
