package com.javapractise.daily.concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ReentrantSpinLock implements Lock {
    private volatile AtomicReference<Thread> owner = new AtomicReference<>();

    /*
        为了实现可重入锁，我们需要引入一个计数器，用来记录一个重复获取锁的次数
        此变量为同一个线程在操作，没有必要加上volatile保障可见性和有序性
     */
    private int count = 0;


    @Override
    public void lock() {
        Thread t = Thread.currentThread();
        if (t == owner.get()) {
            ++count;
            return;
        }

        while (!owner.compareAndSet(null, t)) {
            Thread.yield();
        }
    }

    @Override
    public void unlock() {
        Thread t = Thread.currentThread();
        if (t == owner.get()) {
            if (count > 0) {
                --count;
            } else {
                owner.set(null);
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }



    @Override
    public Condition newCondition() {
        return null;
    }
}
