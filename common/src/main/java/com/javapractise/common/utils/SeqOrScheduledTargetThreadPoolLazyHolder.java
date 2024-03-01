package com.javapractise.common.utils;

import static com.javapractise.common.utils.ThreadUtils.CustomThreadFactory;
import static com.javapractise.common.utils.ThreadUtils.shutdownThreadPoolGracefully;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledThreadPoolExecutor;


@Slf4j
public class SeqOrScheduledTargetThreadPoolLazyHolder {
    static final ScheduledThreadPoolExecutor EXECUTOR = new ScheduledThreadPoolExecutor(1,
            new CustomThreadFactory("set"));

    public static ScheduledThreadPoolExecutor getInnerExecutor() {
        return EXECUTOR;
    }

    static {
        log.info("thread pool has been initialized");

        Runtime.getRuntime().addShutdownHook(
                new ShutdownHookThread("Scheduled and Sequential Task ThreadPool", new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        shutdownThreadPoolGracefully(EXECUTOR);
                        return null;
                    }
                }));
    }
}
