package com.javapractise.daily.concurrency;


import com.javapractise.common.utils.Logger;
import com.google.common.util.concurrent.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GuavaFutureDemo {
    public static final int SLEEP_GAP = 5000;

    public static String getCurThreadName() {
        return Thread.currentThread().getName();
    }

    static class HotWaterJob implements Callable<Boolean> {
        @Override
        public Boolean call() throws Exception {
            try {
                Logger.info("洗好水壶");
                Logger.info("灌上凉水");
                Logger.info("放在火上");

                Thread.sleep(SLEEP_GAP);
                Logger.info("水开发");
            } catch (InterruptedException e) {
                Logger.info(" 发生异常被中断");
                return false;
            }

            Logger.info(" 烧水工作，运行结束.");
            return true;
        }
    }

    static class WashJob implements Callable<Boolean> {
        @Override
        public Boolean call() throws Exception {
            try {
                Logger.info("洗茶壶");
                Logger.info("洗茶杯");
                Logger.info("拿茶叶");
                //线程睡眠一段时间，代表清洗中
                Thread.sleep(SLEEP_GAP);
                Logger.info("洗完了");
            } catch (InterruptedException e) {
                Logger.info(" 清洗工作 发生异常被中断.");
                return false;
            }

            Logger.info(" 清洗工作  运行结束.");
            return true;
        }
    }

    static class MainJob implements Runnable {
        volatile boolean waterOk = false;
        volatile boolean cupOk = false;

        int gap = SLEEP_GAP / 10;
        @Override
        public void run() {
            while (true) {
                try {
                    Logger.info("读书中...");
                    Thread.sleep(gap);
                } catch (InterruptedException e) {
                    Logger.info(getCurThreadName() + "发生异常被中断.");
                }
            }
        }

        public void drinkTea() {
            if (waterOk && cupOk) {
                Logger.info("泡茶喝，茶喝完");
                this.waterOk = false;
                this.gap = SLEEP_GAP * 100;
            } else if (!waterOk) {
                Logger.info("烧水 没有完成，没有茶喝了");
            } else if (!cupOk ) {
                Logger.info("洗杯子  没有完成，没有茶喝了");
            }
        }
    }

    public static void main(String[] args) {
        MainJob mainJob = new MainJob();
        Thread mainThread = new Thread(mainJob);
        mainThread.setName("喝茶线程");
        mainThread.start();

        Callable<Boolean> hotJob = new HotWaterJob();
        Callable<Boolean> washJob = new WashJob();

        ExecutorService jPool = Executors.newFixedThreadPool(10);
        ListeningExecutorService gPool = MoreExecutors.listeningDecorator(jPool);
        ListenableFuture<Boolean> hotFuture = gPool.submit(hotJob);

        Futures.addCallback(hotFuture, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                Logger.info("烧水成功，尝试喝茶");

                if (aBoolean) {
                    mainJob.waterOk = false;
                    mainJob.drinkTea();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Logger.info("烧水失败，没有茶喝了");
            }
        }, MoreExecutors.directExecutor());

        ListenableFuture<Boolean> washFuture = gPool.submit(washJob);
        Futures.addCallback(washFuture, new FutureCallback<Boolean>() {
            public void onSuccess(Boolean r) {
                Logger.info("杯子洗  成功，尝试喝茶");
                if (r) {
                    mainJob.cupOk = true;
                    mainJob.drinkTea();
                }
            }

            public void onFailure(Throwable t) {
                Logger.info("杯子洗不了，没有茶喝了");
            }
        }, MoreExecutors.directExecutor());
    }
}
