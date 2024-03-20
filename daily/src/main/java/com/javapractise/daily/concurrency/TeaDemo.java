package com.javapractise.daily.concurrency;

import com.javapractise.common.utils.Print;

public class TeaDemo {
    public static final int SLEEP_GAP = 500;

    public static String getCurThreadName() {
        return Thread.currentThread().getName();
    }

    static class HotWaterThread extends Thread {
        public HotWaterThread() {
            super("boil water-thread");
        }

        public void run() {
            try {
                Print.tcfo("wash the kettle");
                Print.tcfo("fill with cold water");
                Print.tcfo("put it on the fire");
                Thread.sleep(SLEEP_GAP);
                Print.tcfo("the water is boiling");
            } catch (InterruptedException e) {
                Print.tcfo(" interrupted by exception");
            }
            Print.tcfo(" run over");
        }
    }

    static class WashThread extends Thread {
        public WashThread() {
            super("$$ wash-thread");
        }

        public void run() {
            try {
                Print.tcfo("wash cattle");
                Print.tcfo("wash cup");
                Print.tcfo("get tea leaves");
                //线程睡眠一段时间，代表清洗中
                Thread.sleep(SLEEP_GAP);
                Print.tcfo("wash over");

            } catch (InterruptedException e) {
                Print.tcfo(" interrupted by exception.");
            }
            Print.tcfo(" run over.");
        }
    }
    public static void main(String[] args) {
        Thread hThread = new HotWaterThread();
        Thread wThread = new WashThread();

        hThread.start();
        wThread.start();
        try {
            hThread.join();
            wThread.join();

            Thread.currentThread().setName("main thread");
            Print.tcfo("brew tea to drink");
        } catch (InterruptedException e) {
            Print.tcfo(getCurThreadName() + " has exception and interrupted.");
        }
        Print.tcfo(getCurThreadName() + " run over.");
    }
}
