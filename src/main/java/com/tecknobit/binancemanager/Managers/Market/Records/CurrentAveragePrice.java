package com.tecknobit.binancemanager.Managers.Market.Records;

public class CurrentAveragePrice {

    private final int mins;
    private final double price;

    public CurrentAveragePrice(int mins, double price) {
        this.mins = mins;
        this.price = price;
    }

    public int getMins() {
        return mins;
    }

    public double getPrice() {
        return price;
    }

}
