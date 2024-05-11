package com.javapractise.daily.designpattern;

public class SleepingState implements State {
    @Override
    public void writeProgram(Work w) {
        System.out.println(String.format("当前时间: %d点，不行了，睡着了", w.getHour()));
    }
}
