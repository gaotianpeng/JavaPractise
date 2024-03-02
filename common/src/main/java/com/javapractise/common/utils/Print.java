package com.javapractise.common.utils;

import com.javapractise.common.utils.ReflectionUtils;

public class Print {
    /*
        threadName + context
     */
    public static void tco(Object s) {
        String str = String.format("[%s]: %s", Thread.currentThread().getName(), s);
        ThreadUtils.seqExecutor(() -> {
            System.out.println(str);
        });
    }

    /*
        threadName + className + methodName
     */
    public static void tcfo(Object s) {
        String str = String.format("[%s|%s]:%s", Thread.currentThread().getName(),
                ReflectionUtils.getNakeCallClassMethod(), s);
        System.out.println(str);
    }
}
