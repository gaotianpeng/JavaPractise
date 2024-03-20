package com.javapractise.daily.concurrency;

import com.javapractise.common.utils.Print;
import jdk.nashorn.internal.codegen.CompilerConstants;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class JavaFutureDemo {
    public static final int SLEEP_GAP = 500;

    static class HotWaterJob implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            try {
                Print.tcfo("洗好水壶");
                Print.tcfo("灌上凉水");
                Print.tcfo("放在火上");
                Thread.sleep(SLEEP_GAP);
            } catch (InterruptedException e) {
                Print.tcfo(" interrupted by exception");
                return false;
            }

            Print.tcfo(" run over.");
            return true;
        }
    }

    static class WashJob implements Callable<Boolean> {
        @Override
        public Boolean call() throws Exception {
            try {
                Print.tcfo("洗茶壶");
                Print.tcfo("洗茶杯");
                Print.tcfo("拿茶叶");
                //线程睡眠一段时间，代表清洗中
                Thread.sleep(SLEEP_GAP);
                Print.tcfo("洗完了");

            } catch (InterruptedException e) {
                Print.tcfo(" 清洗工作 发生异常被中断.");
                return false;
            }
            Print.tcfo(" 清洗工作  运行结束.");
            return true;
        }
    }

    public static void drinkTea(boolean waterOk, boolean cupOk) {
        if (waterOk && cupOk) {
            Print.tcfo("泡茶喝");
        } else if (!waterOk) {
            Print.tcfo("烧水失败，没有茶喝了");
        } else if (!cupOk) {
            Print.tcfo("杯子洗不了，没有茶喝了");
        }
    }
    public static void main(String[] args) {
        Thread.currentThread().setName("main thread");
        Callable<Boolean> hJob = new HotWaterJob();
        FutureTask<Boolean> hTask = new FutureTask<>(hJob);
        Thread hotThread = new Thread(hTask, "** 烧水-Thread");

        Callable<Boolean> wJob = new WashJob();
        FutureTask<Boolean> wTask =
                new FutureTask<>(wJob);
        Thread washThread = new Thread(wTask, "$$ 清洗-Thread");
        hotThread.start();
        washThread.start();

        try {
            boolean waterOk = hTask.get();
            boolean cupOk = wTask.get();
            drinkTea(waterOk, cupOk);
        } catch (InterruptedException e) {
            Print.tcfo(Thread.currentThread().getName() + "发生异常被中断.");
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
