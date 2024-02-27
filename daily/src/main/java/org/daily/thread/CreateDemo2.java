package org.daily.thread;

import java.lang.annotation.Target;

public class CreateDemo2 {
    public static final int MAX_TURN = 5;
    static int threadNo = 1;

    static class RunTarget implements Runnable {
        public void run() {
            for (int i = 0; i < MAX_TURN; i++) {
                System.out.println(Thread.currentThread().getName() + ", turn " + i);
            }
            System.out.println(Thread.currentThread().getName() + " run over");
        }
    }
    public static void main(String[] args) {
        Thread th = null;
        // method 1
        for (int i = 0; i < 2; ++i) {
            RunTarget target = new RunTarget();
            th = new Thread(target, "RunnableThread" + threadNo++);
            th.start();
        }

        // method 2
        for (int i = 0; i < 2; i++) {
            th = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 1; j < MAX_TURN; j++) {
                        System.out.println(Thread.currentThread().getName() + ", turn：" + j);
                    }
                    System.out.println(Thread.currentThread().getName() + " run over");
                }
            }, "RunnableThread" + threadNo++);
            th.start();
        }

        // method 3
        for (int i = 0; i < 2; i++) {
            th = new Thread(() ->  {
                for (int j = 1; j < MAX_TURN; j++) {
                    System.out.println(Thread.currentThread().getName() + ", turn：" + j);
                }
                System.out.println(Thread.currentThread().getName() + " run over");
            }, "RunnableThread" + threadNo++);
            th.start();
        }
        System.out.println(Thread.currentThread().getName() + " run over.");
    }
}

