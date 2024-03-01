package com.javapractise.common.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class ShutdownHookThread extends Thread {
    private volatile boolean hasShutdown = false;

    private static AtomicInteger shutdownTimes = new AtomicInteger(0);

    private final Callable callback;

    public ShutdownHookThread(String name, Callable callback) {
        super("jvm exit hook(" + name + ")");
        this.callback = callback;
    }

    @Override
    public void run() {
        synchronized (this) {
            System.out.println(getName() + " starting...");
            if (!this.hasShutdown) {
                this.hasShutdown = true;
                long beginTime = System.currentTimeMillis();
                try {
                    this.callback.call();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(getName() + " error: " + e.getMessage());
                }

                long consumingTimeTotal = System.currentTimeMillis() - beginTime;
                System.out.println(getName() + " cost(ms)" + consumingTimeTotal);
            }
        }
    }
}
