package com.javapractise.daily.designpattern;

public class NoonState implements State {

    @Override
    public void writeProgram(Work w) {
        if (w.getHour() < 13) {
            System.out.println(String.format("当前时间: %d点, 饿了，午饭；犯困，午休", w.getHour()));
        } else {
            w.setState(new AfternoonState());
            w.writeProgram();
        }
    }
}
