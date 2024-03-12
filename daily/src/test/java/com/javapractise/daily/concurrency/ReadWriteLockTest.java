package com.javapractise.daily.concurrency;

import com.javapractise.common.utils.DateUtils;
import com.javapractise.common.utils.Print;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {
    final static Map<String, String> MAP = new HashMap<>();
    final static ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock();
    final static Lock READ_LOCK = LOCK.readLock();
    final static Lock WRITE_LOCK = LOCK.writeLock();

    public static Object put(String key, String value) {
        WRITE_LOCK.lock();
        try {
            Print.tco(DateUtils.getNowTime() + " 抢占了WRITE_LOCK，开始执行write操作");
            Thread.sleep(1000);
            String put = MAP.put(key, value);
            return put;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            WRITE_LOCK.unlock();
        }
        return null;
    }

    public static Object get(String key) {
        READ_LOCK.lock();
        try {
            Print.tco(DateUtils.getNowTime() + " 抢占了READ_LOCK，开始执行read操作");
            Thread.sleep(1000);
            String value = MAP.get(key);
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

        for (int i = 0; i < 4; i++) {
            new Thread(readTarget, "read thread" + i).start();
        }
        for (int i = 0; i < 2; i++) {
            new Thread(writeTarget, "write thread" + i).start();
        }
    }
}
