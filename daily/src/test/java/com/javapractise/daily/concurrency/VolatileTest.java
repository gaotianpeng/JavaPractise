package com.javapractise.daily.concurrency;

import com.javapractise.common.utils.Print;
import com.javapractise.common.utils.ThreadUtils;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantLock;

public class VolatileTest {
    private volatile long value;

    @Test
    public void testAtomicLong() {
        final int TASK_AMOUNT = 10;

        ExecutorService pool = ThreadUtils.getCpuIntenseTargetThreadPool();

        final int TURNS = 10000;
        CountDownLatch countDownLatch = new CountDownLatch(TASK_AMOUNT);
        long start = System.currentTimeMillis();
        for (int i = 0; i < TASK_AMOUNT; ++i) {
            pool.submit(() -> {
                try {
                    for (int j = 0; j < TURNS; j++) {
                        value++;
                    }
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
        Print.tcfo("sum is: " + value);
        Print.tcfo("the diff is: " + (TURNS * TASK_AMOUNT - value));
    }
}
