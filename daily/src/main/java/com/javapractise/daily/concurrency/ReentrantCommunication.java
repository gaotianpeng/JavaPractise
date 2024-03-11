package com.javapractise.daily.concurrency;

import com.javapractise.common.utils.Print;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantCommunication {
    static Lock lock = new ReentrantLock();
    static private Condition condition = lock.newCondition();

    static class WaitTarget implements Runnable {
        @Override
        public void run() {
            lock.lock();
            try {
                Print.tcfo("i am the waiter");
                condition.await();
                Print.tco("receive signal, waiter continue run");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    static class NotifyTarget implements Runnable {

        @Override
        public void run() {
            lock.lock();
            try {
                Print.tcfo("i am the sender");
                condition.signal();
                Print.tco("sended the signal, but not release lock right now");
            } finally {
                lock.unlock();
            }
        }
    }

//    @Test
//    public void CommunicationTest() throws InterruptedException {
//        Thread waitThread = new Thread(new WaitTarget(), "WaitThread");
//        waitThread.start();
//
//        Thread.sleep(1000);
//
//        Thread notifyThread = new Thread(new NotifyTarget(), "NotifyThread");
//        notifyThread.start();
//    }

    public static void main(String[] args) throws InterruptedException {
        Thread waitThread = new Thread(new WaitTarget(), "WaitThread");
        waitThread.start();

        Thread.sleep(1000);

        Thread notifyThread = new Thread(new NotifyTarget(), "NotifyThread");
        notifyThread.start();
    }
}
