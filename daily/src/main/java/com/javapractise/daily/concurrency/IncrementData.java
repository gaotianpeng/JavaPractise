package com.javapractise.daily.concurrency;

import java.util.concurrent.locks.Lock;

public class IncrementData {
    public static int sum = 0;

    public static void lockAndFastIncrease(Lock lock) {
        lock.lock();
        try {
            sum++;
        } finally {
            lock.unlock();
        }
    }
}
