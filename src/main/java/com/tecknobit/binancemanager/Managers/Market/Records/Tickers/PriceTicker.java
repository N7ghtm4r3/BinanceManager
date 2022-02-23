package com.tecknobit.binancemanager.Managers.Market.Records.Tickers;

public class PriceTicker extends Ticker{

    private final double price;

    public PriceTicker(String symbol, double price) {
        super(symbol);
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

}
