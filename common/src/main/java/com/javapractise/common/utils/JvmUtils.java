package com.javapractise.common.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class JvmUtils {
    public static final int getProcessID() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        String jvmInstanceName = runtimeMXBean.getName();
        return Integer.valueOf(jvmInstanceName.split("@")[0]).intValue();
    }
}
