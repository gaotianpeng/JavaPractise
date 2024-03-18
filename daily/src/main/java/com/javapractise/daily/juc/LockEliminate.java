package com.javapractise.daily.juc;

public class LockEliminate {
    private static final int CIRCLE = 2000000;

    public static String createStringBuffer(String s1, String s2) {
        StringBuffer sb = new StringBuffer();
        sb.append(s1);
        sb.append(s2);
        return sb.toString();
    }

    // 关闭锁消除：-server -XX:+DoEscapeAnalysis -XX:-EliminateLocks -Xcomp -XX:-BackgroundCompilation -XX:BiasedLockingStartupDelay=0
    // 开启锁消除：-server -XX:+DoEscapeAnalysis -XX:+EliminateLocks -Xcomp -XX:-BackgroundCompilation -XX:BiasedLockingStartupDelay=0
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < CIRCLE; i++) {
            createStringBuffer("JVM", "Diagnostis");
        }
        long bufferCost = System.currentTimeMillis() - start;
        System.out.println("createStringBuffer:" + bufferCost + "ms");
    }
}
