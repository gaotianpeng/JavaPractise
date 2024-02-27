package org.daily.thread;

public class CreateDemo {
    public static final int MAX_TURN = 5;

    static int threadNo = 1;

    static class DemoThread extends Thread {
        public DemoThread() {
            super("Thread-" + threadNo++);
        }

        public void run() {
            for (int i = 1; i < MAX_TURN; i++) {
                System.out.println(getName() + " turn: " + i);
            }
            System.out.println(getName() + "run over");
        }
    }

    public static void main(String[] args) {
        Thread th = null;
        for (int i = 0; i < 2; i++) {
            th = new DemoThread();
            th.start();
        }

        System.out.println(Thread.currentThread().getName() + " run over");
    }
}
