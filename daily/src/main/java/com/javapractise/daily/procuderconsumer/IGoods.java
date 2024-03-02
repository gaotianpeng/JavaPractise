package com.javapractise.daily.procuderconsumer;

import com.javapractise.common.utils.RandomUtils;

public interface IGoods extends Comparable<IGoods> {
    void setId(int id);

    enum Type {
        PET,
        FOOD,
        CLOTHES;

        public static Type randType() {
            int length = values().length;
            int typeNo = RandomUtils.randInMod(length) - 1;
            return values()[typeNo];
        }
    }

    int getID();

    float getPrice();

    void setPrice(float price);

    String getName();

    int getAmount();

    Type getType();
}
