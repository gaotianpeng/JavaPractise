package org.daily.concurrency;

public class NotSafePlus {
    public static final int MAX_TURN = 1000000;

    static class NotSafeCounter  implements Runnable {
        public int amount = 0;

        public void increase() {
            amount++;
        }
        @Override
        public void run() {
            int turn = 0;
            while (turn < MAX_TURN) {
                ++turn;
                increase();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        NotSafeCounter counter = new NotSafeCounter();
        for (int i = 0; i < 10; ++i) {
            Thread th = new Thread(counter);
            th.start();
        }

        Thread.sleep(2000);

        System.out.println("right result " + MAX_TURN * 10);
        System.out.println("real result " + counter.amount);

        System.out.println("diff " + (MAX_TURN * 10 - counter.amount));
    }
}
