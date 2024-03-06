package com.javapractise.daily.thread;

import com.javapractise.common.utils.Print;
import static com.javapractise.common.utils.ThreadUtils.sleepSeconds;

public class WaitNotifyDemo {
    static Object locker = new Object();

    static class WaitTarget implements Runnable {
        public void run() {
            synchronized (locker) {
                try {
                    System.out.println("start to wait");
                    locker.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("receive notify, continue to execute");
            }
            System.out.println("wait exit");
        }
    }

    static class NotifyTarget implements Runnable {
        public void run() {
            synchronized (locker) {
                Print.consoleInput();
                locker.notifyAll();
                System.out.println("sended notify, but not release lock right now");

            }
            System.out.println("notify exit");
        }
    }
    public static void main(String[] args) {
        Thread waitThread = new Thread(new WaitTarget(), "WaitThread");
        waitThread.start();
        sleepSeconds(1);
        Thread notifyThread = new Thread(new NotifyTarget(), "NotifyThread");
        notifyThread.start();

        System.out.println("exit");
    }
}
