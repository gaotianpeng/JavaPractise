package com.javapractise.common.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public class ThreadUtils {
    public static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    public static int KEEP_ALIVE_SECONDS = 30;

    public static final int QUEUE_SIZE = 10000;
    public static final int MIXED_CORE = 0;  //混合线程池核心线程数
    public static final int MIXED_MAX = 128;  //最大线程数

    public static final int MAXIMUM_POOL_SIZE = CPU_COUNT;

    public static final int IO_MAX = Math.max(2, CPU_COUNT * 2);

    public static int IO_CORE = 0;


    public static Thread getCurrentThread() {
        return Thread.currentThread();
    }

    public static void sleepSeconds(int second) {
        LockSupport.parkNanos(second * 1000L * 1000L * 100L);
    }

    public static ScheduledThreadPoolExecutor getSeqOrScheduledExecutorService() {
        return SeqOrScheduledTargetThreadPoolLazyHolder.getInnerExecutor();
    }
    public static void seqExecutor(Runnable command) {
        getSeqOrScheduledExecutorService().execute(command);
    }

    public static void sleepMilliSeconds(int millisecond) {
        LockSupport.parkNanos(millisecond * 1000L * 1000L);
    }

    public static String getCurrentThreadName() {
        return Thread.currentThread().getName();
    }

    public static class CustomThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);

        private final ThreadGroup group;

        private final AtomicInteger threadNumber = new AtomicInteger(1);

        private final String threadTag;

        public CustomThreadFactory(String tag) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            this.threadTag = "apppool-" + poolNumber.getAndIncrement() + "-" + tag + "-";
        }

        @Override
        public Thread newThread(Runnable target) {
            Thread th = new Thread(group, target, threadTag + threadNumber.getAndIncrement(), 0);
            if (th.isDaemon()) {
                th.setDaemon(false);
            }

            if (th.getPriority() != Thread.NORM_PRIORITY) {
                th.setPriority(Thread.NORM_PRIORITY);
            }

            return th;
        }
    }


    public static void shutdownThreadPoolGracefully(ExecutorService threadPool) {
        if (!(threadPool instanceof ExecutorService) || threadPool.isTerminated()) {
            return;
        }

        try {
            threadPool.shutdown();
        } catch (SecurityException e) {
            return;
        } catch (NullPointerException e) {
            return;
        }

        try {
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
                if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("task in thread pool is not finish");
                }
            }
        } catch (InterruptedException e) {
            threadPool.shutdownNow();
        }

        if (!threadPool.isTerminated()) {
            try {
                for (int i = 0; i < 100; ++i) {
                    if (threadPool.awaitTermination(10, TimeUnit.MICROSECONDS)) {
                        break;
                    }
                    threadPool.shutdownNow();
                }
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            } catch (Throwable e) {
                System.err.println(e.getMessage());
            }
        }
    }
}

