package com.javapractise.daily.thread;

public class JoinDemo {
    public static final int SLEEP_GAP = 5000;
    public static final int MAX_TURN = 50;

    static class SleepThread extends Thread {
        static int threadSeqNumber = 1;
        public SleepThread() {
            super("sleepThread-" + threadSeqNumber);
            threadSeqNumber++;
        }

        public void run() {
            System.out.println(getName() + " enter into sleep.");
            try {
                Thread.sleep(SLEEP_GAP);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(getName() + " interrupted by exception");
                return;
            }

            System.out.println(getName() + " run over.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread th1 = new SleepThread();
        Thread.sleep(20);
        System.out.println("start thread1");
        th1.start();
        th1.join();

        Thread.sleep(20);
        System.out.println("start thread2");
        Thread th2 = new SleepThread();
        th2.start();
        th2.join(5000);

        System.out.println("main thread run over");
    }
}
