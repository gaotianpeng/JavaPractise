package com.javapractise.daily.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class YieldDemo {
    public static final int MAX_TURN = 100;
    public static AtomicInteger index = new AtomicInteger(0);

    private static Map<String, AtomicInteger> metric = new HashMap<>();
    private static void printMetric() {
        System.out.println("metric = " + metric);
    }

    static class YieldThread extends Thread {
        static int threadSeqNumber = 1;
        public YieldThread() {
            super("YieldThread-" + threadSeqNumber);
            metric.put(this.getName(), new AtomicInteger(0));
        }

        public void run() {
            for (int i = 1; i < MAX_TURN && index.get() < MAX_TURN; i++) {
                System.out.println("thread priority " + getPriority());
                index.incrementAndGet();
                metric.get(this.getName()).incrementAndGet();

                if (i % 2 == 0) {
                    Thread.yield();
                }
            }

            System.out.println("metric = " + metric);
            System.out.println(getName() + " run over.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new YieldThread();
        thread1.setPriority(Thread.MAX_PRIORITY);
        Thread thread2 = new YieldThread();
        thread2.setPriority(Thread.MIN_PRIORITY);
        System.out.println("start thread");
        thread1.start();
        thread2.start();
        Thread.sleep(100);
    }
}
