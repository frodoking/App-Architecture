package com.frodo.android.app.core.task;

import java.util.concurrent.ExecutorService;

import com.frodo.android.app.framework.net.NetworkCallTask;
import com.frodo.android.app.framework.task.AbstractBackgroundExecutor;
import com.frodo.android.app.framework.task.BackgroundCallTask;

import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import retrofit.RetrofitError;

/**
 * Created by frodo on 2015/7/6.
 */
public class BackgroundExecutorImpl extends AbstractBackgroundExecutor {

    private static final Handler sHandler = new Handler(Looper.getMainLooper());

    public BackgroundExecutorImpl(ExecutorService executorService) {
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

            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    mBackgroundCallTask.preExecute();
                }
            });

            R result = mBackgroundCallTask.runAsync();

            sHandler.post(new ResultCallback(result));
        }

        private class ResultCallback implements Runnable {
            private final R mResult;

            private ResultCallback(R result) {
                mResult = result;
            }

            @Override
            public void run() {
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
            android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    mNetworkCallTask.onPreCall();
                }
            });

            R result = null;
            RetrofitError retrofitError = null;

            try {
                result = mNetworkCallTask.doBackgroundCall();
            } catch (RetrofitError re) {
                retrofitError = re;
            } catch (Exception e) {
                e.printStackTrace();
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
