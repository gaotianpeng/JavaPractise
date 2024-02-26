package org.daily.thread;

public class StackAreaDemo {
    public static void main(String[] args) {
        System.out.println("current thread name: " + Thread.currentThread().getName());
        System.out.println("current thread id: " + Thread.currentThread().getId());
        System.out.println("current thread state: " + Thread.currentThread().getState());
        System.out.println("current thread priority: " + Thread.currentThread().getPriority());
    }
}
