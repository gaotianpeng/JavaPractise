package org.daily.thread;

public class EmptyThreadDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread th = new Thread();
        System.out.println("thread name: " + th.getName());
        System.out.println("thread id: " + th.getId());
        System.out.println("thread state: " + th.getState());
        System.out.println("thread priority: " + th.getPriority());
        th.start();
        System.out.println("thread state: " + th.getState());
    }
}
