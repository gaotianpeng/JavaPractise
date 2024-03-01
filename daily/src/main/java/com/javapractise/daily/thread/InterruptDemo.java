package com.javapractise.daily.thread;

public class InterruptDemo {
    public static final int SLEEP_GAP = 5000;
    public static final int MAX_TURN = 50;

    static class SleepThread extends Thread {
        static int threadSeqNumber = 1;

        public SleepThread() {
            super("sleep thread -" + threadSeqNumber);
            threadSeqNumber++;
        }

        public void run() {
            try {
                System.out.println(getName() + " enter sleep");
                Thread.sleep(SLEEP_GAP);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(getName() + " occur interrupted exception");
                return;
            }

            System.out.println(getName() + " run over");
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Thread th1 = new SleepThread();
        th1.start();
        Thread th2 = new SleepThread();
        th2.start();

        Thread.sleep(2000);

        th1.interrupt();
        Thread.sleep(5000);
        th2.interrupt();
        Thread.sleep(1000);
        System.out.println("main run over");
    }
}
