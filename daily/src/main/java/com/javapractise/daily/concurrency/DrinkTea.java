package com.javapractise.daily.concurrency;

import com.javapractise.common.utils.Print;

import java.util.concurrent.CompletableFuture;

public class DrinkTea {
    private static final int SLEEP_GAP = 3;

    public static void main(String[] args) {
        CompletableFuture<Boolean> washJob =
                CompletableFuture.supplyAsync(() -> {
                    Print.tcfo("洗茶杯");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Print.tcfo("洗完了");
                    return true;
                });

        CompletableFuture<Boolean> hotJob =
                CompletableFuture.supplyAsync(() -> {
                   Print.tcfo("洗好水壶");
                   Print.tcfo("烧开水");

                    try {
                        Thread.sleep(SLEEP_GAP);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return true;
                });

        CompletableFuture<String> drinkJob =
                washJob.thenCombine(hotJob, (hotOk, washOk) -> {
                    if (hotOk && washOk) {
                        Print.tcfo("泡茶喝，茶喝完");
                        return "茶喝完了";
                    }

                    return "没有喝到茶";
                });

        Print.tco(drinkJob.join());
    }
}
