package org.daily.thread;

public class PriorityDemo {
    public static final int SLEEP_GAP = 10000;

    static class PrioritySetThread extends Thread {
        static int thredNo = 1;
        public long opportunities = 0;
        public PrioritySetThread() {
            super("thread-" + thredNo);
            thredNo++;
        }

        public void run() {
            for (int i = 0; ; i++) {
                opportunities++;
            }
        }

    }
    public static void main(String[] args) throws InterruptedException {
        PrioritySetThread[] threads = new PrioritySetThread[10];
        for (int i = 0; i < threads.length; ++i) {
            threads[i] = new PrioritySetThread();
            threads[i].setPriority(i+1);
        }

        for (int i = 0; i < threads.length; ++i) {
            threads[i].start();
        }

        Thread.sleep(SLEEP_GAP);
        for (int i = 0; i < threads.length; ++i) {
            threads[i].stop();
        }


        for (int i = 0; i < threads.length; i++) {
            System.out.println(threads[i].getName() +
                    ";priority-" + threads[i].getPriority() +
                    ";opportunities-" + threads[i].opportunities
            );
        }
    }
}
