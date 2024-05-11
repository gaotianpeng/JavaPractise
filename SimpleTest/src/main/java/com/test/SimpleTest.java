package com.test;

public class SimpleTest {
    public SimpleTest() {
    }


    public String evaluate(String value) {
        return simpleTest1(value);
    }

    public Integer evaluate(Integer value) {
        return simpleTest2(value);
    }



    public static String simpleTest1(String var) {
        return var + " simpleTest1";
    }

    public static int simpleTest2(int var) {
        return var + 1;
    }
}
