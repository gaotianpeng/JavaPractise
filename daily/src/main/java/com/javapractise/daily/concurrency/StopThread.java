package com.javapractise.daily.concurrency;

public class StopThread {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("main thread");
        Thread th = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("th thread is running");
            }
            System.out.println("th thread run over!!!");
        });

        th.start();
        Thread.sleep(100);
        th.interrupt();
        System.out.println("man thread run over");
    }
}
