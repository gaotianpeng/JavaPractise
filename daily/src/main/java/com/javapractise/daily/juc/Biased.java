package com.javapractise.daily.juc;

import java.util.List;
import java.util.Vector;

public class Biased {
    public static List<Integer> numberList = new Vector<>();

    // 启用偏向锁：-XX:+UseBiasedLocking  -XX:BiasedLockingStartupDelay=0  -client  -Xmx512m -Xms512m
    // 禁用偏向锁：-XX:-UseBiasedLocking -client -Xmx512m -Xms512m
    public static void main(String[] args) throws InterruptedException {
        long begin = System.currentTimeMillis();
        int count = 0;
        int startNum = 0;
        while (count < 10000000) {
            numberList.add(startNum);
            startNum += 2;
            count++;
        }
        long end = System.currentTimeMillis();
        System.out.println(end - begin);
    }
}
