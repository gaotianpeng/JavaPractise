package com.javapractise.daily.designmode;

import com.javapractise.common.utils.Print;
import com.javapractise.common.utils.ThreadUtils;

import java.util.concurrent.TimeUnit;

public class MasterWorkerTest {
    static class SimpleTask extends Task<Integer> {
        @Override
        protected Integer doExecute() {
            Print.tcfo("task " + getId() + " is done");
            return getId();
        }
    }

    public static void main(String[] args) {
        Master<SimpleTask, Integer> master = new Master<>(4);

        ThreadUtils.scheduleAtFixedRate(() -> master.submit(
                        new SimpleTask()), 2, TimeUnit.SECONDS);

        ThreadUtils.scheduleAtFixedRate(() -> master.printResult(), 5, TimeUnit.SECONDS);
    }
}
