package com.javapractise.common.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.javapractise.common.utils.ThreadUtils.*;


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

//        log.info("线程池已经初始化");


        EXECUTOR.allowCoreThreadTimeOut(true);
        //JVM关闭时的钩子函数
        Runtime.getRuntime().addShutdownHook(new ShutdownHookThread("混合型任务线程池", new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                //优雅关闭线程池
                shutdownThreadPoolGracefully(EXECUTOR);
                return null;
            }
        }));
    }
}
