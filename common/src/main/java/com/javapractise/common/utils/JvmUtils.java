package com.javapractise.common.utils;

import sun.misc.Unsafe;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Field;

public class JvmUtils {
    public static final int getProcessID() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        String jvmInstanceName = runtimeMXBean.getName();
        return Integer.valueOf(jvmInstanceName.split("@")[0]).intValue();
    }

    // get Unsafe instance
    public static Unsafe getUnsafe() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

}
