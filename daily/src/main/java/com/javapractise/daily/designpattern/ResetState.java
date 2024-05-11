package com.javapractise.daily.designpattern;

public class ResetState implements State {
    @Override
    public void writeProgram(Work w) {
        System.out.println(String.format("当前时间: %d点，下班回家了", w.getHour()));
    }
}
