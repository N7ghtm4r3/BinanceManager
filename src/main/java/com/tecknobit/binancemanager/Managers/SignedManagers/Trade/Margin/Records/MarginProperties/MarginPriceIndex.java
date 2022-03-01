package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginProperties;

public class MarginPriceIndex {

    private final long calcTime;
    private final double price;
    private final String symbol;

    public MarginPriceIndex(long calcTime, double price, String symbol) {
        this.calcTime = calcTime;
        this.price = price;
        this.symbol = symbol;
    }

    public long getCalcTime() {
        return calcTime;
    }

    public double getPrice() {
        return price;
    }

    public String getSymbol() {
        return symbol;
    }

}
