package com.javapractise.daily.concurrency;

import com.javapractise.common.utils.Print;
import static com.javapractise.common.utils.Print.syncTco;

import java.util.concurrent.locks.Lock;

public class IncrementData {
    public static int sum = 0;

    public static void lockAndFastIncrease(Lock lock) {
        Print.syncTco(" -- 开始抢占锁");
        lock.lock();
        try {
            Print.syncTco(" ^-^ 抢到了锁");

            sum++;
        } finally {
            lock.unlock();
        }
    }
}
