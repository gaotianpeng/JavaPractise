package com.javapractise.common.utils;


public class Logger {
    public static void debug(Object s) {
        String content = null;
        if (null != s) {
            content = s.toString().trim();
        } else {
            content = "";
        }

        String out = String.format("%20s |>  %s", ReflectionUtils.getNakeCallClassMethod(), content);
        Print.tcfo(out);
    }

    synchronized public static void info(Object s) {
        String content = null;
        if (null != s) {
            content = s.toString().trim();
        } else {
            content = "";
        }
        String cft = "[" + Thread.currentThread().getName() + "|"
                + ReflectionUtils.getNakeCallClassMethod() + "]";
        String out = String.format("%20s |>  %s ", cft, content);
        Print.tcfo(out);
    }

    synchronized public static void info(Object... args) {
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < args.length; ++i) {
            content.append(args[i] != null ? args[i].toString() : "null");
            content.append(" ");
        }

        String cft = "[" + Thread.currentThread().getName() + "|"
                + ReflectionUtils.getNakeCallClassMethod() + "]";
        String out = String.format("%20s |> %s ", cft, content.toString());
        Print.tcfo(out);
    }
}
