package com.javapractise.common.utils;

public class ReflectionUtils {
    // get className+methodName
    public static String getNakeCallClassMethod() {
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        String[] className = stack[3].getClassName().split("\\.");
        return className[className.length - 1] + "." + stack[3].getMethodName();
    }
}
