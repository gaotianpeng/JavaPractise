package com.javapractise.daily.concurrency;

import com.javapractise.common.utils.Print;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {
    @Test
    public void testReentrantLock() {
        final int TURNS = 1000;
        final int THREADS = 10;

        ExecutorService pool = Executors.newFixedThreadPool(THREADS);

        Lock lock = new ReentrantLock();
        CountDownLatch countDownLatch = new CountDownLatch(THREADS);

        long start = System.currentTimeMillis();
        for (int i = 0; i < THREADS; i++) {
            pool.submit(() -> {
                try {
                    for (int j = 0; j < TURNS; j++) {
                        IncrementData.lockAndFastIncrease(lock);
                    }
                    Print.tco(" thread finish sum");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        float time = (System.currentTimeMillis() - start) / 1000F;
        Print.tcfo("run time is:" + time);
        Print.tcfo("sum is:" + IncrementData.sum);
    }

    @Test
    public void testCLHLockCapability() {
        final int TURNS = 100000;
        final int THREADS = 10;

        ExecutorService pool = Executors.newFixedThreadPool(THREADS);
        Lock lock = new CLHLock();

        CountDownLatch countDownLatch = new CountDownLatch(THREADS);
        long start = System.currentTimeMillis();
        for (int i = 0; i < THREADS; i++) {
            pool.submit(() -> {
                for (int j = 0; j < TURNS; j++) {
                    IncrementData.lockAndFastIncrease(lock);
                }
                Print.tcfo("this thread finish accumulation");
                countDownLatch.countDown();
            });
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        float time = (System.currentTimeMillis() - start)/1000F;
        Print.tcfo("run time is:" + time);
        Print.tcfo("sum result is:" + IncrementData.sum);
    }

    @Test
    public void testNotFairLock() throws InterruptedException {
        Lock lock = new ReentrantLock(false);

        Runnable r = ()->IncrementData.lockAndFastIncrease(lock);
        Thread[] thArr = new Thread[4];
        for (int i = 0; i < 4; ++i) {
            thArr[i] = new Thread(r, "thread" + i);
        }
        for (int i = 0; i < 4; ++i) {
            thArr[i].start();
        }
        Thread.sleep(Integer.MAX_VALUE);
    }
}
