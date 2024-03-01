package com.javapractise.daily.thread;


public class DaemonDemo {
    public static final int SLEEP_GAP = 500;
    public static final int MAX_TURN = 4;

    static class DaemonThread extends Thread {
        public DaemonThread() {
            super("daemonThread");
        }

        public void run() {
            System.out.println("--daemon thread start..");
            for (int i = 1; ; i++) {
                System.out.println("--turn：" + i + "--daemon status:" + isDaemon());
                // 线程睡眠一会
                try {
                    Thread.sleep(SLEEP_GAP);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
    public static void main(String[] args) {
        Thread daemonThread = new DaemonThread();
        daemonThread.setDaemon(true);
        daemonThread.start();

        Thread userThread = new Thread(() -> {
            System.out.println(">>user thread start.");
            for (int i = 1; i <= MAX_TURN; i++) {
                System.out.println(">>turn：" + i + " -daemon status:" + Thread.currentThread().isDaemon());
                try {
                    Thread.sleep(SLEEP_GAP);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println(">> user thread run over");
        }, "userThread");
        userThread.start();

        System.out.println(" daemon status:" + Thread.currentThread().isDaemon());

        System.out.println("run over");
    }
}
