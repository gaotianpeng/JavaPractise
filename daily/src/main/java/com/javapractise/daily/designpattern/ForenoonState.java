package com.javapractise.daily.designpattern;

public class ForenoonState implements State{
    public void writeProgram(Work w) {
        if (w.getHour() < 12) {
            System.out.println(String.format("当前时间: %d点, 上午工作，精神百倍", w.getHour()));
        } else {
            w.setState(new NoonState());
            w.writeProgram();
        }
    }
}
