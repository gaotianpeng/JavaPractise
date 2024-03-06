package com.javapractise.daily.procuderconsumer;

import com.javapractise.common.utils.JvmUtils;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommunicatePetStore {
    public static final int MAX_AMOUNT = 10;

    static class DataBuffer<T> {
        private List<T> dataList = new LinkedList<>();
        private volatile int amount = 0;
        private final Object LOCK_OBJECT = new Object();
        private final Object NOT_FULL = new Object();
        private final Object NOT_EMPTY = new Object();

        public void add(T element) throws Exception {
            synchronized (NOT_FULL) {
                while (amount >= MAX_AMOUNT) {
                    System.out.println("queue is full");
                    NOT_FULL.wait();
                }
            }

            synchronized (LOCK_OBJECT) {
                if (amount < MAX_AMOUNT) { // double check
                    dataList.add(element);
                    ++amount;
                }
            }

            synchronized (NOT_EMPTY) {
                NOT_EMPTY.notify();
            }
        }

        public T fetch() throws Exception {
            synchronized (NOT_EMPTY) {
                while (amount <= 0) {
                    System.out.println("queue is empty!");
                    NOT_EMPTY.wait();
                }
            }

            T element = null;
            synchronized (LOCK_OBJECT) {
                if (amount > 0) {
                    element = dataList.remove(0);
                    --amount;
                }
            }

            synchronized (NOT_FULL) {
                NOT_FULL.notify();
            }

            return element;
        }
    }


    public static void main(String[] args) {
        System.out.println("current process id is " + JvmUtils.getProcessID());
        System.setErr(System.out);

        DataBuffer<IGoods> dataBuffer = new DataBuffer<>();

        Callable<IGoods> produceAction = () -> {
            IGoods goods = Goods.produceOne();
            dataBuffer.add(goods);
            return goods;
        };

        Callable<IGoods> consumerAction = () -> {
            IGoods goods = null;
            goods = dataBuffer.fetch();
            return goods;
        };

        final int THREAD_TOTAL = 20;
        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_TOTAL);

        final int CONSUMER_TOTAL = 11;
        final int PRODUCE_TOTAL = 1;
        for (int i = 0; i < PRODUCE_TOTAL; ++i) {
            threadPool.submit(new Producer(produceAction, 50));
        }

        for (int i = 0; i < CONSUMER_TOTAL; ++i) {
            threadPool.submit(new Consumer(consumerAction, 100));
        }
    }

}
