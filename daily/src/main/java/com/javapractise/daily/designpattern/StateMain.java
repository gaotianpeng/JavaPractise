package com.javapractise.daily.designpattern;

public class StateMain {

    public static void main(String[] args) {
        State1.Work emergencyProjects = new State1.Work();

        emergencyProjects.hour = 9;
        emergencyProjects.writeProgram();
        emergencyProjects.hour = 10;
        emergencyProjects.writeProgram();
        emergencyProjects.hour = 12;
        emergencyProjects.writeProgram();
        emergencyProjects.hour = 13;
        emergencyProjects.writeProgram();
        emergencyProjects.hour = 14;
        emergencyProjects.writeProgram();
        emergencyProjects.hour = 17;
        emergencyProjects.writeProgram();

        emergencyProjects.finish = false;
        emergencyProjects.hour = 19;
        emergencyProjects.writeProgram();
        emergencyProjects.hour = 22;
        emergencyProjects.writeProgram();
    }
}
