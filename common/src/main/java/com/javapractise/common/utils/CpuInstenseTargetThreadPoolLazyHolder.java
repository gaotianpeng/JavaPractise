package com.javapractise.common.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.javapractise.common.utils.ThreadUtils.*;

public class CpuInstenseTargetThreadPoolLazyHolder {
    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(
            MAXIMUM_POOL_SIZE,
            MAXIMUM_POOL_SIZE,
            KEEP_ALIVE_SECONDS,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(QUEUE_SIZE),
            new CustomThreadFactory("cpu"));

    public static ThreadPoolExecutor getInnerExecutor() {
        return EXECUTOR;
    }

    static {
        EXECUTOR.allowCoreThreadTimeOut(true);
        Runtime.getRuntime().addShutdownHook(
                new ShutdownHookThread("cpu intensive thread pool", new Callable() {
                    @Override
                    public Object call() throws Exception {
                        shutdownThreadPoolGracefully(EXECUTOR);
                        return null;
                    }
                }));

    }

}
