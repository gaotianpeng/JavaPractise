package org.daily.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CreateDemo3 {
    public static final int MAX_TURN = 5;
    public static final int COMPUTE_TIMES = 100000000;

    static class ReturnableTask implements Callable<Long> {
        @Override
        public Long call() throws Exception {
            long startTime = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + " thread start");
            Thread.sleep(1000);

            for (int i = 0; i < COMPUTE_TIMES; ++i) {
                int j = i*10000;
            }
            long used = System.currentTimeMillis() - startTime;
            System.out.println(Thread.currentThread().getName() + " thread run over");
            return used;
        }
    }
    public static void main(String[] args) throws InterruptedException {
        ReturnableTask task = new ReturnableTask();
        FutureTask<Long> futureTask = new FutureTask<>(task);
        Thread th = new Thread(futureTask, "returnableThread");
        th.start();

        Thread.sleep(500);
        System.out.println(Thread.currentThread().getName() + " do something for myself");
        for (int i = 0; i < COMPUTE_TIMES / 2; ++i) {
            int j = i * 10000;
        }

        System.out.println(Thread.currentThread().getName() + " get current task result");

        try {
            System.out.println(th.getName() +  " cost time " + futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + " run over");
    }
}
