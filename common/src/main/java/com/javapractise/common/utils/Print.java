package com.javapractise.common.utils;

import com.javapractise.common.utils.ReflectionUtils;

import java.util.Scanner;

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

    public static void syncTco(Object s) {
        String cft = "[" + Thread.currentThread().getName() + "]" + "：" + s;
        System.out.println(cft);
    }

    /*
        threadName + className + methodName
     */
    public static void tcfo(Object s) {
        String str = String.format("[%s|%s]:%s", Thread.currentThread().getName(),
                ReflectionUtils.getNakeCallClassMethod(), s);
        System.out.println(str);
    }

    public static String consoleInput() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter sth: ");
        String nextLine = sc.nextLine();
        return nextLine;
    }

    public static void fo(Object s) {
        String cft = "[" + ReflectionUtils.getNakeCallClassMethod() + "]";

        ThreadUtils.seqExecutor(() -> {
            System.out.println(cft + "：" + s);
        });
    }
}
