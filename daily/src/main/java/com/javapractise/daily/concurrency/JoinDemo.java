package com.javapractise.daily.concurrency;

public class JoinDemo {
    public static final int SLEEP_GAP = 5000;
    public static final int MAX_TURN = 50;

    static class SleepThread extends Thread  {
        static int threadSeqNumber = 1;

        public SleepThread() {
            super("sleepThread-" + threadSeqNumber);
            threadSeqNumber++;
        }

        public void run() {
            try {
                System.out.println(getName() + " enter sleep");
                Thread.sleep(SLEEP_GAP);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(getName() + "interrupted by exception");
                return;
            }

            System.out.println(getName() + " run over");
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new SleepThread();
        Thread.sleep(1000);
        System.out.println(" start thread1");
        thread1.start();
        thread1.join();
        Thread.sleep(2000);

        System.out.println(" start thread2");
        Thread thread2 = new SleepThread();
        thread2.start();
        thread2.join(1000);

        System.out.println(" man thread over");
    }
}
