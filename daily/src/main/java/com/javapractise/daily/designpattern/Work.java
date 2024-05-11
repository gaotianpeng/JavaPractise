package com.javapractise.daily.designpattern;

public class Work {
    private State current;
    private double hour;
    private boolean finish = false;

    public Work() {
        current = new ForenoonState();
        hour = 0;
    }

    public double getHour() {
        return hour;
    }

    public void setHour(double hour) {
        this.hour = hour;
    }

    public void setFinished(boolean finish) {
        this.finish = finish;
    }

    public boolean getFinished() {
        return finish;
    }

    public void writeProgram() {
        current.writeProgram(this);
    }

    public void setState(State state) {
        current = state;
    }
}
