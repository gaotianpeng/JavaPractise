package com.practise.common;

import java.util.HashSet;
import java.util.Random;

public class RandomTool {
    static final int DB_PREFIX_LEN = 3;
    static final int USER_PREFIX_LEN = 5;
    static final int TABLE_PREFIX_LEN = 4;
    static final int ROLE_PREFIX_LEN = 5;

    public static final String DB_PREFIX = "db_";
    public static final String USER_PREFIX = "user_";
    public static final String ROLE_PREFIX = "role_";
    public static final String TABLE_PREFIX = "tbl_";
    public static final int MIN_STR_LEN = 8;
    static final int MIN_N = 5;
    private static final int MAX_LEN = 20;
    private static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_";
    private static Random random = new Random();

    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            char c = chars.charAt(index);
            sb.append(c);
        }
        return sb.toString();
    }

    public static String randomDBName(int maxLength) {
        if (maxLength > MAX_LEN) {
            maxLength = MAX_LEN;
        }
        return generateRandomString(maxLength);
    }

    public static String randomUserName(int maxLength) {
        if (maxLength <= 0) {
            throw new IllegalArgumentException("maxLength must be greater than 0");
        }
        if (maxLength > MAX_LEN) {
            maxLength = MAX_LEN;
        }
        return generateRandomString(maxLength);
    }

    public static String randomRoleName(int maxLength) {
        if (maxLength <= 0) {
            throw new IllegalArgumentException("maxLength must be greater than 0");
        }
        if (maxLength > MAX_LEN) {
            maxLength = MAX_LEN;
        }
        return generateRandomString(maxLength);
    }


    public static String randomTableName(int maxLength) {
        if (maxLength < MIN_STR_LEN) {
            maxLength = MIN_STR_LEN;
        }
        // 初始化StringBuilder，以“tbl_”开头
        StringBuilder sb = new StringBuilder("tbl_");
        sb.append(generateRandomString(maxLength - TABLE_PREFIX_LEN));
        return sb.toString();
    }

    public static int randomNumber(int maxNumber) {
        return new Random().nextInt(maxNumber);
    }

    public static HashSet<String> randomDBNames(int maxLength, int maxNumber) {
        int actualN = randomNumber(maxNumber);
        HashSet<String> res = new HashSet<>();
        for (int i = 0; i < actualN; ++i) {
            res.add(randomDBName(maxLength));
        }
        return res;
    }

    public static HashSet<String> randomUserNames(int maxLength, int maxNumber) {
        int actualN = randomNumber(maxNumber);
        HashSet<String> res = new HashSet<>();
        for (int i = 0; i < actualN; ++i) {
            res.add(randomUserName(maxLength));
        }
        return res;
    }

    public static HashSet<String> randomRoleNames(int maxLength, int maxNumber) {
        int actualN = randomNumber(maxNumber);
        HashSet<String> res = new HashSet<>();
        for (int i = 0; i < actualN; ++i) {
            res.add(randomRoleName(maxLength));
        }
        return res;
    }

    public static HashSet<String> randomTableNames(int maxLength, int maxNumber) {
        int actualN = randomNumber(maxNumber);
        HashSet<String> res = new HashSet<>();
        for (int i = 0; i < actualN; ++i) {
            res.add(randomTableName(maxLength));
        }
        return res;
    }
}

