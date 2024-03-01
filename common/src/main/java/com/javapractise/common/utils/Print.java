package com.javapractise.common.utils;

public class Print {
    /*
     */
    public static void tco(Object s) {
        String str = String.format("[%s]: %s", Thread.currentThread().getName(), s);
        ThreadUtils.seqExecutor(() -> {
            System.out.println(str);
        });
    }
}
