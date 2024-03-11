package com.javapractise.daily.concurrency;

import com.javapractise.common.utils.Print;
import com.javapractise.daily.concurrency.IncrementData;

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
}
