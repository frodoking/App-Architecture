package com.frodo.app.android.core.task;

import com.frodo.app.framework.log.Logger;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * A prioritized {@link ThreadPoolExecutor} for running jobs in App. Created by frodo on 2015/8/13.
 */
public class AndroidExecutor extends ThreadPoolExecutor {
    private static final String TAG = "AndroidExecutor";
    private static final String DEFAULT_NAME = "frodo-pool";

    /**
     * Constructor to build a fixed thread pool with the given pool size.
     *
     * @param poolSize The number of threads.
     */
    public AndroidExecutor(int poolSize) {
        this(poolSize, new DefaultThreadFactory());
    }

    /**
     * Constructor to build a fixed thread pool with the given pool size.
     *
     * @param poolSize                  The number of threads.
     * @param uncaughtThrowableStrategy Dictates how the pool should handle uncaught and unexpected
     *                                  throwables thrown by Futures run by the pool.
     */
    public AndroidExecutor(int poolSize, UncaughtThrowableStrategy uncaughtThrowableStrategy) {
        this(poolSize, new DefaultThreadFactory(uncaughtThrowableStrategy));
    }

    /**
     * Constructor to build a fixed thread pool with the given pool size.
     *
     * @param name     The prefix for threads created by this pool.
     * @param poolSize The number of threads.
     */
    public AndroidExecutor(String name, int poolSize) {
        this(poolSize, new DefaultThreadFactory(name));
    }

    /**
     * Constructor to build a fixed thread pool with the given pool size.
     *
     * @param name                      The prefix for each thread name.
     * @param poolSize                  The number of threads.
     * @param uncaughtThrowableStrategy use to handle uncaught exceptions.
     */
    public AndroidExecutor(String name, int poolSize, UncaughtThrowableStrategy uncaughtThrowableStrategy) {
        this(poolSize, new DefaultThreadFactory(name, uncaughtThrowableStrategy));
    }

    private AndroidExecutor(int corePoolSize, ThreadFactory threadFactory) {
        super(corePoolSize, corePoolSize, 0, TimeUnit.MILLISECONDS,
                new PriorityBlockingQueue<Runnable>(), threadFactory);
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
        return new ComparableFutureTask<>(runnable, value);
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return new ComparableFutureTask<>(callable);
    }

    protected class ComparableFutureTask<V> extends FutureTask<V> implements Comparable<ComparableFutureTask<V>> {
        private Object object;

        public ComparableFutureTask(Callable<V> callable) {
            super(callable);
            object = callable;
        }

        public ComparableFutureTask(Runnable runnable, V result) {
            super(runnable, result);
            object = runnable;
        }

        @Override
        @SuppressWarnings("unchecked")
        public int compareTo(ComparableFutureTask<V> o) {
            if (this == o) {
                return 0;
            }
            if (o == null) {
                return -1; // high priority
            }
            if (object != null && o.object != null && object.getClass().equals(o.object.getClass()) && object instanceof Comparable) {
                return ((Comparable) object).compareTo(o.object);
            }
            return 0;
        }
    }

    /**
     * A strategy for handling unexpected and uncaught throwables thrown by futures run on the pool.
     */
    public enum UncaughtThrowableStrategy {
        /**
         * Silently catches and ignores the uncaught throwables.
         */
        IGNORE,
        /**
         * Logs the uncaught throwables using {@link #TAG} and {@link Logger}.
         */
        LOG {
            @Override
            protected void handle(Throwable t) {
                Logger.fLog().tag(TAG).e("Request threw uncaught throwable", t);
            }
        },
        /**
         * Rethrows the uncaught throwables to crash the app.
         */
        THROW {
            @Override
            protected void handle(Throwable t) {
                super.handle(t);
                if (t != null) {
                    throw new RuntimeException("Request threw uncaught throwable", t);
                }
            }
        };

        protected void handle(Throwable t) {
            // Ignore.
        }
    }

    /**
     * A {@link java.util.concurrent.ThreadFactory} that builds threads with priority {@link
     * android.os.Process#THREAD_PRIORITY_BACKGROUND}.
     */
    private static final class DefaultThreadFactory implements ThreadFactory {
        private final String name;
        private final UncaughtThrowableStrategy uncaughtThrowableStrategy;
        private int threadNum = 0;

        DefaultThreadFactory() {
            this(DEFAULT_NAME);
        }

        DefaultThreadFactory(String name) {
            this(name, UncaughtThrowableStrategy.LOG);
        }

        DefaultThreadFactory(UncaughtThrowableStrategy uncaughtThrowableStrategy) {
            this(DEFAULT_NAME, uncaughtThrowableStrategy);
        }

        DefaultThreadFactory(String name, UncaughtThrowableStrategy uncaughtThrowableStrategy) {
            this.name = name;
            this.uncaughtThrowableStrategy = uncaughtThrowableStrategy;
        }

        @Override
        public Thread newThread(Runnable runnable) {
            final Thread result = new Thread(runnable, name + "-thread-" + threadNum) {
                @Override
                public void run() {
                    android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                    try {
                        super.run();
                    } catch (Throwable t) {
                        uncaughtThrowableStrategy.handle(t);
                    }
                }
            };
            threadNum++;
            return result;
        }
    }
}

