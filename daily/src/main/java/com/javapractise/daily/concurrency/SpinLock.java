package com.javapractise.daily.concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class SpinLock implements Lock {
    private volatile AtomicReference<Thread> owner = new AtomicReference<>();

    @Override
    public void lock() {
        Thread t = Thread.currentThread();
        while (!owner.compareAndSet(null, t)) {
            Thread.yield();
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
    public void unlock() {
        Thread t = Thread.currentThread();
        if (t == owner.get()) {
            owner.set(null);
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
