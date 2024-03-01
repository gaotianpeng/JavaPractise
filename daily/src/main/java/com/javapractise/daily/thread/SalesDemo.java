package com.javapractise.daily.thread;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public class SalesDemo {
    public static final int MAX_AMOUNT = 5;
    public static void sleepMilliSeconds(int millisecond) {
        LockSupport.parkNanos(millisecond * 1000L * 1000L);
    }
    static class StoreGoods extends Thread {
        StoreGoods(String name) {
            super(name);
        }

        private int goodsAmount = MAX_AMOUNT;

        public void run() {
            for (int i = 0; i <= MAX_AMOUNT; ++i) {
                if (this.goodsAmount > 0) {
                    System.out.println(Thread.currentThread().getName()
                        + " sales one. left: " + (--goodsAmount));
                    sleepMilliSeconds(10);
                }
            }
            System.out.println(Thread.currentThread().getName() + " run over");
        }
    }

    static class MallGoods implements Runnable {
        private AtomicInteger goodsAmount = new AtomicInteger(MAX_AMOUNT);

        @Override
        public void run() {
            for (int i = 0; i <= MAX_AMOUNT; ++i) {
                if (this.goodsAmount.get() > 0) {
                    System.out.println(Thread.currentThread().getName()
                        + " sales one. left: " + (goodsAmount.decrementAndGet()));
                }
            }
            System.out.println(Thread.currentThread().getName() + " run over");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("/-- shop ");
        for (int i = 1; i <= 2; i++) {
            Thread thread = null;
            thread = new StoreGoods("shop assistant-" + i);
            thread.start();
        }

        Thread.sleep(1000);

        System.out.println("/-- mall");
        MallGoods mallGoods = new MallGoods();
        for (int i = 1; i <= 2; i++) {
            Thread thread = null;
            thread = new Thread(mallGoods, "store salesman-" + i);
            thread.start();
        }

        System.out.println(Thread.currentThread().getName() + " run over");
    }

}
