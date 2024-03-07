package com.javapractise.daily.concurrency;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;


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
}
