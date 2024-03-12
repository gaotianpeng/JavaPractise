package com.javapractise.daily.concurrency;

import com.javapractise.common.utils.Print;
import com.javapractise.common.utils.ThreadUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

public class Driver {
    private static final int N = 100;

    static class Person implements Runnable {
        private final CountDownLatch doneSignal;
        private final int i;

        Person(CountDownLatch doneSignal, int i) {
            this.doneSignal = doneSignal;
            this.i = i;
        }

        @Override
        public void run() {
            try {
                Print.tcfo("the " + i + " person arrived");
                doneSignal.countDown();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch doneSignal = new CountDownLatch(N);
        Executor e = ThreadUtils.getCpuIntenseTargetThreadPool();
        for (int i = 1; i <= N; ++i) {
            e.execute(new Person(doneSignal, i));
        }

        doneSignal.await();
        Print.tcfo("all arrived, drive");
    }
}
