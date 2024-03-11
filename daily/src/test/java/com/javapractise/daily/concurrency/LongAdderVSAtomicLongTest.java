package com.javapractise.daily.concurrency;

import com.javapractise.common.utils.Print;
import com.javapractise.common.utils.ThreadUtils;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class LongAdderVSAtomicLongTest {
    final int TURNS = 100000000;

    @Test
    public void testAtomicLong() {
        final int TASK_AMOUNT = 10;

        ExecutorService pool = ThreadUtils.getCpuIntenseTargetThreadPool();
        AtomicLong atomicLong = new AtomicLong(0);

        CountDownLatch countDownLatch = new CountDownLatch(TASK_AMOUNT);
        long start = System.currentTimeMillis();
        for (int i = 0; i < TASK_AMOUNT; ++i) {
            pool.submit(() ->{
                try {
                    for (int j = 0; j < TURNS; j++) {
                        atomicLong.incrementAndGet();
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

        Print.tcfo("run time is :" + time);
        Print.tcfo("sum is :" + atomicLong.get());
    }

    @Test
    public void testLongAdder() {
        final int TASK_AMOUNT = 10;
        ExecutorService pool = ThreadUtils.getCpuIntenseTargetThreadPool();

        LongAdder longAdder = new LongAdder();
        CountDownLatch countDownLatch = new CountDownLatch(TASK_AMOUNT);
        long start = System.currentTimeMillis();
        for (int i = 0; i < TASK_AMOUNT; ++i) {
            pool.submit(() -> {
                try {
                    for (int j = 0; j < TURNS; j++) {
                        longAdder.add(1);
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
        Print.tcfo("run time is:" +  time);
        Print.tcfo("sum result is:" + longAdder.longValue());
    }
}
