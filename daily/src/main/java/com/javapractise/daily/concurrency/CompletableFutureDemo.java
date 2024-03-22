package com.javapractise.daily.concurrency;

import com.javapractise.common.utils.Logger;
import com.javapractise.common.utils.ThreadUtils;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureDemo {
    private static final int SLEEP_GA = 5;

    public static void main(String[] args) throws InterruptedException {
        CompletableFuture<Boolean> washJob =
                CompletableFuture.supplyAsync(() -> {
                    Logger.tcfo("洗茶杯 starting ");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Logger.tcfo("洗完了");

                    return true;
                });
        CompletableFuture<Boolean> hotJob =
                CompletableFuture.supplyAsync(() ->{
                    Logger.tcfo("烧开水 starting");
                    Logger.tcfo("烧开水");

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Logger.tcfo("水开了");
                    return true;
                });

        CompletableFuture<String> drinkJob =
                washJob.thenCombine(hotJob, (hotOk, washOk) -> {
                    if (hotOk && washOk) {
                        Logger.tcfo("泡茶跑，茶喝完");
                        return "茶喝完了";
                    }

                    return "没有喝到茶";
        });

        Thread.sleep(10000);
        Logger.tcfo(" main thread over");

    }
}
