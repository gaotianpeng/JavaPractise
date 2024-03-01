package com.javapractise.daily.thread;

public class ThreadNameDemo {
    private static final int MAX_TURN = 3;

    static class RunTarget implements Runnable {

        @Override
        public void run() {
            for (int turn = 0; turn < MAX_TURN; turn++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + " " + turn);
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        RunTarget target = new RunTarget();
        new Thread(target).start();
        new Thread(target).start();
        new Thread(target).start();
        new Thread(target, "use set a ").start();
        new Thread(target, "use set b ").start();
        Thread.sleep(Integer.MAX_VALUE);
    }
}
