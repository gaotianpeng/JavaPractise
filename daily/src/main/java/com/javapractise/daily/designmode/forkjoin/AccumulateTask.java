package com.javapractise.daily.designmode.forkjoin;

import com.javapractise.common.utils.Print;
import java.util.concurrent.RecursiveTask;

public class AccumulateTask extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 10;

    private int start;
    private int end;

    public AccumulateTask(int start, int end) {
        this.start = start;
        this.end = end;
    }
    @Override
    protected Integer compute() {
        int sum = 0;
        boolean canCompute = (end-start) <= THRESHOLD;
        if (canCompute) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }

            Print.tco("执行任务，计算" + start + " 到 " + end + "的和，结果是：" + sum);
        } else {
            Print.tco("切割任务：将" + start + "到" + end + "的和一分为二");
            int middle = (start + end) / 2;
            AccumulateTask ltask = new AccumulateTask(start, middle);
            AccumulateTask rtask = new AccumulateTask(middle + 1, end);
            ltask.fork();
            rtask.fork();

            int leftResult = ltask.join();
            int rightReault = rtask.join();

            sum = leftResult + rightReault;
        }
        return sum;
    }
}
