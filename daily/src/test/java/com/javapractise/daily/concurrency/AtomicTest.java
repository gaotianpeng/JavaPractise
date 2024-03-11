package com.javapractise.daily.concurrency;

import com.javapractise.common.utils.Print;
import com.javapractise.common.utils.ThreadUtils;
import org.junit.Test;
import static com.javapractise.common.utils.ThreadUtils.sleepMilliSeconds;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicStampedReference;


public class AtomicTest {
    private static final int THREAD_COUNT = 10;

    @Test
    public void atomicIntegerTest() {
        int temvalue = 0;
        AtomicInteger i = new AtomicInteger(0);

        temvalue = i.getAndSet(3); // set newValue, return oldValue
        System.out.println("temvalue:" + temvalue + ";  i:" + i.get()); // 0, 3

        temvalue = i.getAndIncrement();
        System.out.println("temvalue:" + temvalue + ";  i:" + i.get()); // 3, 4

        temvalue = i.getAndAdd(5); // 4, 9
        System.out.println("temvalue:" + temvalue + ";  i:" + i.get()); // 4, 9

        boolean flag = i.compareAndSet(9, 100);
        System.out.println("flag: " + flag + ";  i:" + i.get()); // true, 100
    }

    @Test
    public void testAtomicIntegerArray() {
        int temvalue = 0;
        int[] array = {1, 2, 3, 4, 5, 6};

        AtomicIntegerArray i = new AtomicIntegerArray(array);
        temvalue = i.getAndSet(0, 2);
        System.out.println("temvalue:" + temvalue + ";  i:" + i);

        temvalue = i.getAndIncrement(0);
        System.out.println("temvalue:" + temvalue + ";  i:" + i);

        temvalue = i.getAndAdd(0, 5);
        System.out.println("temvalue:" + temvalue + ";  i:" + i);
    }

    @Test
    public void testAtomicStampedReference() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        AtomicStampedReference<Integer> atomicStampedRef =
                        new AtomicStampedReference<>(1, 0);
        ThreadUtils.getMixedTargetThreadPool().submit(new Runnable() {
            @Override
            public void run() {
                boolean success = false;
                int stamp = atomicStampedRef.getStamp();
                Print.tco("before sleep 500: value=" + atomicStampedRef.getReference()
                            + " stamp=" + atomicStampedRef.getStamp());
                sleepMilliSeconds(500);
                success = atomicStampedRef.compareAndSet(1, 10, stamp,
                        stamp + 1);

                Print.tco("after sleep 500 cas 1: success=" + success
                            + " value=" + atomicStampedRef.getReference()
                            + " stamp=" + atomicStampedRef.getStamp());
                stamp++;
                success = atomicStampedRef.compareAndSet(10, 1, stamp,
                        stamp + 1);
                Print.tco("after sleep 500 cas 2: success=" + success
                            + " value=" + atomicStampedRef.getReference()
                            + " stamp=" + atomicStampedRef.getStamp());
                latch.countDown();
            }
        });

        ThreadUtils.getMixedTargetThreadPool().submit(new Runnable() {
            @Override
            public void run() {
                boolean success = false;
                int stamp = atomicStampedRef.getStamp();
                Print.tco("before sleep 1000: value=" + atomicStampedRef.getReference()
                    + " stamp=" + atomicStampedRef.getStamp());
                sleepMilliSeconds(1000);
                Print.tco("after sleep 1000: stamp = " + atomicStampedRef.getStamp());
                success = atomicStampedRef.compareAndSet(1, 20,
                        stamp, stamp++);
                Print.tco("after cas 3 1000: success=" + success
                        + " value=" + atomicStampedRef.getReference()
                        + " stamp=" + atomicStampedRef.getStamp());
                latch.countDown();
            }
        });
        latch.await();
    }

    @Test
    public void testAtomicMarkableReference() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        AtomicMarkableReference<Integer> atomicRef =
                new AtomicMarkableReference<>(1, false);
        ThreadUtils.getMixedTargetThreadPool().submit(new Runnable() {
            @Override
            public void run() {
                boolean success = false;
                int value = atomicRef.getReference();
                boolean mark = getMark(atomicRef);
                Print.tco("before sleep 500: value=" + value
                            + " mark= " + mark);
                sleepMilliSeconds(500);
                success = atomicRef.compareAndSet(1, 10, mark, !mark);
                Print.tco("after sleep 500 cas 1: success=" + success
                            + " value=" + atomicRef.getReference()
                            + " mark=" + getMark(atomicRef));
                latch.countDown();
            }
        });

        ThreadUtils.getMixedTargetThreadPool().submit(new Runnable() {
            @Override
            public void run() {
                boolean success = false;
                int value = atomicRef.getReference();
                boolean mark = getMark(atomicRef);
                Print.tco("before sleep 1000: value=" + atomicRef.getReference()
                            + " mark= " + mark);
                sleepMilliSeconds(1000);
                Print.tco("after sleep 1000: mark = " + getMark(atomicRef));
                success = atomicRef.compareAndSet(1, 20, mark, !mark);
                Print.tco("after cas 3 1000: success=" + success
                            + " value=" + atomicRef.getReference()
                            + " mark=" + getMark(atomicRef));
                latch.countDown();
            }
        });
        latch.await();
    }

    private boolean getMark(AtomicMarkableReference<Integer> atomicRef) {
        boolean [] markHolder = {false};
        int val = atomicRef.get(markHolder);
        return markHolder[0];
    }
}


















