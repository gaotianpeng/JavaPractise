package com.javapractise.daily.designpattern;

public class EveningState implements State {
    @Override
    public void writeProgram(Work w) {
        if (w.getFinished()) {
            w.setState(new ResetState());
            w.writeProgram();
        } else {
            if (w.getHour() < 21) {
                System.out.println(String.format("当前时间: %d点，加班哦，疲惫之极", w.getHour()));
            } else {
                w.setState(new SleepingState());
                w.writeProgram();
            }
        }
    }
}
