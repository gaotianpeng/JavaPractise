package com.javapractise.common.utils;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {
    // [1, mod]
    public static int randInMod(int mod) {
        return Math.abs(ThreadLocalRandom.current().nextInt(mod)) + 1;
    }

    // [low high]
    public static int randInRange(int low, int high) {
        return randInMod(high - low) + high;
    }
}
