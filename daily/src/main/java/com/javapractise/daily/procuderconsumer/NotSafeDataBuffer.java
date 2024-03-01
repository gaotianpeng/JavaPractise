package com.javapractise.daily.procuderconsumer;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class NotSafeDataBuffer<T> {
    public static final int MAX_AMOUNT = 10;

    private List<T> dataList = new LinkedList<>();

    private AtomicInteger amount = new AtomicInteger(0);


    public void add(T element) throws Exception {
        if (amount.get() > MAX_AMOUNT) {
            System.out.println("queue is full");
            return;
        }

        dataList.add(element);
    }

}
