package com.javapractise.daily.designmode;

public class Singleton {
    private static volatile Singleton instance;

    // 私有化构造方法
    private Singleton() {
    }

    static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
