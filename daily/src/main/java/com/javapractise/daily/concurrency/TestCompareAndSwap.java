package com.javapractise.daily.concurrency;

import com.javapractise.common.utils.JvmUtils;
import com.javapractise.common.utils.ThreadUtils;
import sun.misc.Unsafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

public class TestCompareAndSwap {
    static class OptimisticLockingPlus {
        private static final int THREAD_COUNT = 10;
        volatile private int value;
        private static final Unsafe unsafe = JvmUtils.getUnsafe();
        private static final long valueOffset;
        private static final AtomicLong failure = new AtomicLong(0);
        static {
            try {
                valueOffset = unsafe.objectFieldOffset(
                        OptimisticLockingPlus.class.getDeclaredField("value"));
                System.out.println("valueOffset:=" + valueOffset);
            } catch (Exception e) {
                throw new Error(e);
            }
        }

        public final boolean unSafeCompareAndSet(int oldValue, int newValue) {
            return unsafe.compareAndSwapInt(this, valueOffset, oldValue, newValue);
        }

        public void selfPlus() {
            int oldValue = value;
            int i = 0;
            do {
                oldValue = value;
                if (i++ > 1) {
                    failure.incrementAndGet();
                }
            } while (!unSafeCompareAndSet(oldValue, oldValue+1));
        }
        public static void main(String[] args) throws InterruptedException {
            final OptimisticLockingPlus cas = new OptimisticLockingPlus();
            CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
            for (int i = 0; i < THREAD_COUNT; ++i) {
                ThreadUtils.getMixedTargetThreadPool().submit(() ->  {
                    for (int j = 0; j < 1000; j++) {
                        cas.selfPlus();
                    }
                    latch.countDown();
                });
            }

            latch.await();
            System.out.println("sum：" + cas.value);
            System.out.println("failed：" + cas.failure.get());
        }
    }
}
