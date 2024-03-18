package com.javapractise.daily.juc;

import java.util.ArrayList;
import java.util.List;

public class ThreadUnSafe {
    public static List<Integer> numberList = new ArrayList<>();
    public static class AddToList implements Runnable {
        int startNum = 0;
        public AddToList(int startNum) {
            this.startNum = startNum;
        }
        @Override
        public void run() {
            int count = 0;
            while (count < 1000000) {
                numberList.add(startNum);
                startNum += 2;
                count++;
            }
        }
    }
    public static void main(String[] args) {
        Thread th1 = new Thread(new AddToList(0));
        Thread th2 = new Thread(new AddToList(1));
        th1.start();
        th2.start();
    }
}
