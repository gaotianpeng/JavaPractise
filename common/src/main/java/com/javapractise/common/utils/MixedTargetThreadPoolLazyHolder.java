package com.javapractise.common.utils;

import static com.javapractise.common.utils.ThreadUtils.*;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MixedTargetThreadPoolLazyHolder {
    private static final int max = (null != System.getProperty(MIXED_THREAD_AMOUNT)) ?
            Integer.parseInt(System.getProperty(MIXED_THREAD_AMOUNT)) : MIXED_MAX;

    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(
            max,
            max,
            KEEP_ALIVE_SECONDS,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue(QUEUE_SIZE),
            new CustomThreadFactory("mixed"));


    public static ThreadPoolExecutor getInnerExecutor() {
        return EXECUTOR;
    }

    static {
        EXECUTOR.allowCoreThreadTimeOut(true);
        Runtime.getRuntime().addShutdownHook(new ShutdownHookThread("mixed task thread pool", new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                shutdownThreadPoolGracefully(EXECUTOR);
                return null;
            }
        }));
    }
}
