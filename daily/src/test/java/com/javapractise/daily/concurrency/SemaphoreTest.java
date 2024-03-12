package com.javapractise.daily.concurrency;

import com.javapractise.common.utils.DateUtils;
import com.javapractise.common.utils.Print;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class SemaphoreTest {
    @Test
    public void testShareLock() throws InterruptedException {
        final int USER_TOTAL = 10;
        final int PERMIT_TOTAL = 2;
        final CountDownLatch countDownLatch = new CountDownLatch(USER_TOTAL);
        final Semaphore semaphore = new Semaphore(PERMIT_TOTAL);
        AtomicInteger index = new AtomicInteger(0);

        Runnable r = () -> {
            try {
                Calendar calendear = new GregorianCalendar();
                semaphore.acquire(1);
                Print.tco(DateUtils.getNowTime() + ",受理处理中...,服务号:" + index.incrementAndGet());
                Thread.sleep(1000);
                semaphore.release(1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            countDownLatch.countDown();
        };

        Thread[] ths = new Thread[USER_TOTAL];
        for (int i = 0; i < USER_TOTAL; ++i) {
            ths[i] = new Thread(r, "thread" + i);
        }
        for (int i = 0; i < USER_TOTAL; ++i) {
            ths[i].start();
        }

        countDownLatch.await();
    }
}
