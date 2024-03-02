package com.javapractise.daily.procuderconsumer;

import com.javapractise.common.utils.Print;
import static com.javapractise.common.utils.ThreadUtils.sleepMilliSeconds;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer implements Runnable {
    // The consume interval, the waiting time before consume again, defaults to 100m
    public static final int CONSUME_GAP = 100;

    static final AtomicInteger TURN = new AtomicInteger(0);
    static final AtomicInteger CONSUMER_NO = new AtomicInteger(1);

    String name;

    Callable action = null;

    int gap = CONSUME_GAP;

    public Consumer(Callable action, int gap) {
        this.action = action;
        this.gap = gap;
        name = "consumer-" + CONSUMER_NO.incrementAndGet();
    }

    public Consumer(Callable action) {
        this.action = action;
        this.gap = CONSUME_GAP;
        name = "consumer-" + CONSUMER_NO.incrementAndGet();
    }

    @Override
    public void run() {
        while (true) {
            TURN.incrementAndGet();
            try {
                Object out = action.call();
                if (null != out) {
                    Print.tcfo("the " + TURN.get() + " of consume: " + out);
                }
                sleepMilliSeconds(gap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
