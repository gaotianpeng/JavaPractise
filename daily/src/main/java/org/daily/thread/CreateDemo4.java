package org.daily.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.LockSupport;

public class CreateDemo4 {
    public static final int MAX_TURN = 5;
    public static final int COMPUTE_TIMES = 100000000;

    public static void sleepMilliSeconds(int millisecond) {
        LockSupport.parkNanos(millisecond * 1000L * 1000L);
    }

    public static void sleepSeconds(int second) {
        LockSupport.parkNanos(second * 1000L * 1000L * 1000L);
    }

    static class DemoThread implements Runnable {
        @Override
        public void run() {
            for (int j = 1; j < MAX_TURN; j++) {
                System.out.println(Thread.currentThread().getName() + ", turn " + j);
                sleepMilliSeconds(10);
            }
        }
    }

    static class ReturnableTask implements Callable<Long> {

        @Override
        public Long call() throws Exception {
            long startTime = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + " thread started");
            for (int j = 1; j < MAX_TURN; j++) {
                System.out.println(Thread.currentThread().getName() + ", turn " + j);
                sleepMilliSeconds(10);
            }

            long used = System.currentTimeMillis() - startTime;
            System.out.println(Thread.currentThread().getName() + " thread run over");
            return used;
        }
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("hello world");
        ExecutorService pool = Executors.newFixedThreadPool(3);
        pool.execute(new DemoThread());
        pool.execute(new Runnable() {
            @Override
            public void run() {
                for (int j = 1; j < MAX_TURN; j++) {
                    System.out.println(Thread.currentThread().getName() + ", turn：" + j);
                    sleepMilliSeconds(10);
                }
            }
        });

        Future future = pool.submit(new ReturnableTask());
        Long result = (Long)future.get();
        System.out.println("async task result is :" + result);

        sleepSeconds(100);
    }
}
