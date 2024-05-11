package com.javapractise.daily.designpattern;

public class AfternoonState implements State {

    @Override
    public void writeProgram(Work w) {
        if (w.getHour() < 17) {
            System.out.println(String.format("当前时间: %d点, 下午状态不错，继续努力", w.getHour()));
        } else {
            w.setState(new EveningState());
            w.writeProgram();
        }
    }
}
