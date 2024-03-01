package com.javapractise.daily.concurrency;

public class SafePlus {
    public static final int MAX_TURN = 1000000;

    static class SafeCounter implements Runnable {
        public int amount = 0;
        public void increase() {
            ++amount;
        }

        @Override
        public void run() {
            int turn = 0;
            while (turn < MAX_TURN) {
                ++turn;
                synchronized (this) {
                    increase();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SafeCounter counter = new SafeCounter();
        for (int i = 0; i < 10; ++i) {
            Thread th = new Thread(counter);
            th.start();
        }

        Thread.sleep(2000);
        System.out.println("right result " + MAX_TURN*10);
        System.out.println("real result" + counter.amount);
        System.out.println("diff " + (MAX_TURN * 10 - counter.amount));
    }
}
