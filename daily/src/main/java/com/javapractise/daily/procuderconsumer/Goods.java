package com.javapractise.daily.procuderconsumer;

import com.javapractise.common.utils.RandomUtils;

import java.util.concurrent.atomic.AtomicInteger;

public class Goods implements IGoods {
    protected float price;
    protected int id;

    protected String goodName;
    protected int amount;

    protected IGoods.Type goodType;

    private static int goodNo;

    protected Goods() {
        this.id = ++goodNo;
        this.amount = 1;
        this.price = 0;
        this.goodName = "unknow good";
    }

    @Override
    public String toString() {
        return String.format("goods{ID=%s,name=%s,price=%s}",
                getID(), getName(), getPrice());
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public float getPrice() {
        return price;
    }

    @Override
    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String getName() {
        return goodName;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public Type getType() {
        return goodType;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public int compareTo(IGoods o) {
        if (o == null) {
            throw new NullPointerException("Good object is null");
        }

        return this.id = o.getID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Goods goods = (Goods) o;
        return id == goods.id;
    }


    public static IGoods produceOne() {
        Type ranodmType = Type.randType();
        return produceByType(ranodmType);
    }

    public static IGoods produceByType(Type type) {
        switch (type) {
            case PET:
                return new GoodsPet();
            case FOOD:
                return new GoodsFood();
            case CLOTHES:
                return new GoodsClothes();
        }

        return new Goods();
    }

    private static class GoodsPet extends Goods {
        private final static AtomicInteger PET_NO = new AtomicInteger(0);
        public GoodsPet() {
            super();
            this.goodType = Type.PET;
            this.goodName = "pet-" + PET_NO.incrementAndGet();
            price = RandomUtils.randInRange(1000, 10000);
            amount = RandomUtils.randInMod(5);
        }
    }

    private static class GoodsClothes extends Goods {
        private final static AtomicInteger CLOTHES_NO = new AtomicInteger(0);

        public GoodsClothes() {
            super();
            this.goodType = Type.CLOTHES;
            this.goodName = "pet clothes-" + CLOTHES_NO.incrementAndGet();
            price = RandomUtils.randInRange(50, 100);
            amount = RandomUtils.randInMod(5);
        }
    }

    private static class GoodsFood extends Goods {
        private final static AtomicInteger FOOD_NO = new AtomicInteger(0);

        public GoodsFood() {
            super();
            this.goodType = IGoods.Type.FOOD;
            this.goodName = "pet food-" + FOOD_NO.incrementAndGet();
            price = RandomUtils.randInRange(50, 100);
            amount = RandomUtils.randInMod(5);
        }
    }
}
