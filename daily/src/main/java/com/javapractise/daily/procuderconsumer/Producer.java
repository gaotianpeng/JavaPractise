package com.javapractise.daily.procuderconsumer;

import com.javapractise.common.utils.Print;
import static com.javapractise.common.utils.ThreadUtils.sleepMilliSeconds;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable {
    // The production interval, the waiting time before producing again, defaults to 200m
    public static final int PRODUCE_GAP = 200;

    static final AtomicInteger TURN = new AtomicInteger(0);

    static final AtomicInteger PRODUCER_NO = new AtomicInteger(1);

    String name = null;

    Callable action = null;

    int gap = PRODUCE_GAP;

    public Producer(Callable action, int gap) {
        this.action = action;
        this.gap = gap;
        if (this.gap <= 0) {
            this.gap = PRODUCE_GAP;
        }

        name = "producer-" + PRODUCER_NO.incrementAndGet();
    }

    public Producer(Callable action) {
        this.action = action;
        this.gap = PRODUCE_GAP;
        name = "producer-" + PRODUCER_NO.incrementAndGet();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Object out = action.call();
                if (null != out) {
                    Print.tcfo("This " + TURN.get() + " turn production:" + out);
                }
                sleepMilliSeconds(gap);
                TURN.incrementAndGet();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
