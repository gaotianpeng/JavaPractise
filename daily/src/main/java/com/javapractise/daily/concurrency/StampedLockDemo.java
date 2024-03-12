package com.javapractise.daily.concurrency;

import com.javapractise.common.utils.DateUtils;
import com.javapractise.common.utils.Print;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;

public class StampedLockDemo {
    final static Map<String, String> MAP = new HashMap<>();
    final static StampedLock STAMPED_LOCK = new StampedLock();

    public static Object put(String key, String value) {
        long stamp = STAMPED_LOCK.writeLock();
        try {
            Print.tco(DateUtils.getNowTime() + " 抢占了WRITE_LOCK，开始执行write操作");
            Thread.sleep(1000);
            String put = MAP.put(key, value);
            return put;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Print.tco(DateUtils.getNowTime() + " 释放了WRITE_LOCK");
            STAMPED_LOCK.unlockWrite(stamp);
        }
        return null;
    }

    public static Object pessimisticRead(String key) {
        Print.tco(DateUtils.getNowTime() + " LOCK进入过写模式，只能悲观读");
        // 写锁已经被抢占，进入了写锁模式，只能获取悲观读锁
        long stamp = STAMPED_LOCK.readLock();
        try {
            Print.tco(DateUtils.getNowTime() + " 抢占了READ_LOCK");
            String value = MAP.get(key); //成功获取到读锁，并重新获取最新的变量值
            return value;
        } finally {
            Print.tco(DateUtils.getNowTime() + " 释放了READ_LOCK");
            STAMPED_LOCK.unlockRead(stamp); //释放读锁
        }
    }

    public static Object optimisticRead(String key) throws InterruptedException {
        String value = null;
        long stamp = STAMPED_LOCK.tryOptimisticRead();
        if (0 != stamp) {
            Print.tco(DateUtils.getNowTime() + "乐观读的印戳值，获取成功");
            Thread.sleep(1000);
            value = MAP.get(key);
        } else {
            Print.tco(DateUtils.getNowTime() + "乐观读的印戳值，获取失败");
            // LOCK已经进入写模式
            return pessimisticRead(key);
        }
        // 验证乐观读的印戳值是否有效，无效则LOCK进入过写模式
        // 乐观读的印戳值无效，表明写锁被占用过
        if (!STAMPED_LOCK.validate(stamp)) {
            Print.tco(DateUtils.getNowTime() + " 乐观读的印戳值，已经过期");
            // 写锁已经被抢占，进入了写锁模式，只能获取悲观读锁
            return pessimisticRead(key);
        } else {
            // 乐观读的印戳值有，表明写锁没有被占用过
            // 如果STAMPED_LOCK写锁没有被抢占，那么可以不用加悲观读锁，减少了读锁的开销
            Print.tco(DateUtils.getNowTime() + " 乐观读的印戳值，没有过期");
            return value;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Runnable writeTarget = () -> put("key", "value");
        Runnable readTarget = () -> {
            try {
                optimisticRead("key");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        new Thread(writeTarget, "write thread").start();
        new Thread(readTarget, "read thread").start();
    }
}
