package com.javapractise.daily.procuderconsumer;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotSafePetStore<T> {
    private static NotSafeDataBuffer<IGoods> notSafeDataBuffer = new NotSafeDataBuffer<>();

    static Callable<IGoods> produceAction = () -> {
        IGoods goods = Goods.produceOne();
        try {
            notSafeDataBuffer.add(goods);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return goods;
    };

    static Callable<IGoods> consumerAction = () -> {
        IGoods goods = null;
        try {
            goods = notSafeDataBuffer.fetch();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return goods;
    };

    public static void main(String[] args) {
        System.setErr(System.out);
        final int THREAD_TOTAL = 20;
        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_TOTAL);
        for (int i = 0; i < 5; ++i) {
            threadPool.submit(new Producer(produceAction, 500));
            threadPool.submit(new Consumer(consumerAction, 500));
        }
    }
}
