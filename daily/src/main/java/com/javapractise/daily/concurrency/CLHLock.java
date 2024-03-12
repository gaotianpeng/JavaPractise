package com.javapractise.daily.concurrency;

import com.javapractise.common.utils.Print;
import lombok.Data;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class CLHLock implements Lock {
    private static ThreadLocal<Node> curNodeLocal = new ThreadLocal<Node>();
    private String name;
    private AtomicReference<Node> tail = new AtomicReference<>(null);

    public CLHLock() {
        tail.getAndSet(Node.EMPTY);
    }

    public CLHLock(String name) {
        this.name = name;
        tail.getAndSet(Node.EMPTY);
    }

    @Override
    public void lock() {
        Node curNode = new Node(true, null);
        Node prevNode = tail.get();
        // CAS自旋：将当前节点插入到队列的尾部
        while (!tail.compareAndSet(prevNode, curNode)) {
            prevNode = tail.get();
        }
        // 设置前驱
        curNode.setPrevNode(prevNode);

        // 监听前驱节点的locked变量，直到其值为false
        // 若前继节点的locked状态为true，则表示前一线程还在抢占或者占有锁
        while (curNode.getPrevNode().isLocked()) {
            Thread.yield();
        }

        /*
            执行到这里，说明当前线程获取到了锁。设置在线程本地变量中，用于释放锁
         */
        curNodeLocal.set(curNode);
    }

    @Override
    public void unlock() {
        Node curNode = curNodeLocal.get();
        curNode.setPrevNode(null);
        curNodeLocal.set(null);
        curNode.setLocked(false);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    @Data
    static class Node {
        public Node(boolean locked, Node prevNode) {
            this.locked = locked;
            this.prevNode = prevNode;
        }

        // true：当前线程正在抢占锁、或者已经占有锁
        // false：当前线程已经释放锁，下一个线程可以占有锁了
        volatile boolean locked;
        // 前一个节点，需要监听其locked字段
        Node prevNode;

        public static final Node EMPTY = new Node(false, null);
    }

    @Override
    public String toString() {
        return "CLHLock{" + name + '}';
    }
}
