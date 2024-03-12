package com.javapractise.daily.concurrency;

import com.javapractise.common.utils.DateUtils;
import com.javapractise.common.utils.Print;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest2 {
    final static Map<String, String> MAP = new HashMap<String, String>();
    final static ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock();
    final static Lock READ_LOCK = LOCK.readLock();
    final static Lock WRITE_LOCK = LOCK.writeLock();

    public static Object put(String key, String value) {
        WRITE_LOCK.lock();
        try {
            Print.tco(DateUtils.getNowTime() + " 抢占了WRITE_LOCK，开始执行write操作");
            Thread.sleep(1000);
            String put = MAP.put(key, value);
            Print.tco(Thread.currentThread().getName() + "尝试降级写锁为读锁");
            READ_LOCK.lock();
            Print.tco(Thread.currentThread().getName() + "写锁降级为读锁成功");
            return put;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            READ_LOCK.unlock();
            WRITE_LOCK.unlock();
        }
        return null;

    }

    //写操作
    public static Object get(String key) {
        READ_LOCK.lock();
        try {
            Print.tco(DateUtils.getNowTime() + " 抢占了READ_LOCK，开始执行read操作");
            Thread.sleep(1000);
            String value = MAP.get(key);
            Print.tco(Thread.currentThread().getName() + "尝试升级读锁为写锁");
            WRITE_LOCK.lock();
            Print.tco(Thread.currentThread().getName() + "读锁升级为写锁成功");

            return value;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            READ_LOCK.unlock();
        }

        return null;
    }

    public static void main(String[] args) {
        Runnable writeTarget = () -> put("key", "value");
        Runnable readTarget = () -> get("key");

        new Thread(writeTarget, "写线程").start();

        new Thread(readTarget, "读线程").start();
    }
}
