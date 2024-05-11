package com.javapractise.daily.designpattern;

import java.io.Console;
import java.io.IOException;

public class State {
    public static class Work {
        private int hour;
        private boolean finish = false;


        public int getHour() {
            return hour;
        }

        public void setHour(int hour) {
            this.hour = hour;
        }

        public boolean taskFinished()  {
            return finish;
        }

        public void setFinish(boolean finish) {
            this.finish = finish;
        }

        public void writeProgram() {
            if (hour < 12) {
                System.out.println(String.format("当前时间: %d点, 上午工作，精神百倍", hour));
            } else if (hour < 13) {
                System.out.println(String.format("当前时间: %d点, 饿了，午饭；犯困，午休", hour));
            } else if (hour < 17) {
                System.out.println(String.format("当前时间: %d点, 下午状态不错，继续努力", hour));
            } else {
                if (finish) {
                    System.out.println(String.format("当前时间: %d点，下班回家了", hour));
                } else {
                    if (hour < 21) {
                        System.out.println(String.format("当前时间: %d点，加班哦，疲惫之极", hour));
                    } else {
                        System.out.println(String.format("当前时间: %d点，不行了，睡着了", hour));
                    }
                }
            }
        }
    }
    

    public static void main(String[] args) {
        Work emergencyProjects = new Work();

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
