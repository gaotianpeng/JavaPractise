package org.daily.thread;

import java.util.ArrayList;
import java.util.List;

public class StatusDemo {
    public static final long MAX_TURN = 5;
    static int threadSeqNumber = 0;
    static List<Thread> threadList = new ArrayList<>();

    private static void printThreadStatus() {
        for (Thread th: threadList) {
            System.out.println(th.getName() + " status : " + th.getState());
        }
    }

    private static void addStatusThread(Thread thread) {
        threadList.add(thread);
    }

    static class StatusDemoThread extends Thread {
        public StatusDemoThread() {
            super("statusPrintThread" + (++threadSeqNumber));
            //将自己加入到全局的静态线程列表
            addStatusThread(this);
        }

        public void run() {
            System.out.println(getName() + ", status: " + getState());
            for (int turn = 0; turn < MAX_TURN; turn++) {
                //线程睡眠
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                //输出所有线程的状态
                printThreadStatus();
            }
            System.out.println(getName() + "- run over.");
        }
    }
    public static void main(String[] args) throws InterruptedException {
        addStatusThread(Thread.currentThread());

        Thread th1 = new StatusDemoThread();
        System.out.println(th1.getName() + "- status: " + th1.getState());
        Thread th2 = new StatusDemoThread();
        System.out.println(th2.getName() + "- status: " + th2.getState());
        Thread th3 = new StatusDemoThread();
        System.out.println(th3.getName() + "- status: " + th3.getState());

        th1.start();

        Thread.sleep(500);
        th2.start();
        Thread.sleep(500);
        th3.start();
        Thread.sleep(10000);
    }
}
