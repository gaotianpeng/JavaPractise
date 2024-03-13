package com.javapractise.daily;

import com.javapractise.common.utils.Print;
import com.javapractise.common.utils.ThreadUtils;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;

public class syncontainer {
    public static void main(String[] args) throws InterruptedException {
        SortedSet<String> elementSet = new TreeSet<>();
        elementSet.add("element 1");
        elementSet.add("element 2");

        SortedSet sortSet = Collections.synchronizedSortedSet(elementSet);
        System.out.println("SortedSet is: " + sortSet);
        CountDownLatch latch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            ThreadUtils.getCpuIntenseTargetThreadPool().submit(
                    () -> {
                        sortSet.add("element " + (3 + finalI));
                        Print.tco("add element" + (3 + finalI));
                        latch.countDown();
                    });
        }
        latch.await();
        System.out.println("Sortedset is :" + sortSet);
    }
}
