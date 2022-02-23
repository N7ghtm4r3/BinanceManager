package com.tecknobit.binancemanager.Managers.Market.Records.Tickers;

public class BookTicker extends Ticker{

    private final double bidPrice;
    private final double bidQty;
    private final double askPrice;
    private final double askQty;

    public BookTicker(String symbol, double bidPrice, double bidQty, double askPrice, double askQty) {
        super(symbol);
        this.bidPrice = bidPrice;
        this.bidQty = bidQty;
        this.askPrice = askPrice;
        this.askQty = askQty;
    }

    public double getBidPrice() {
        return bidPrice;
    }

    public double getBidQty() {
        return bidQty;
    }

    public double getAskPrice() {
        return askPrice;
    }

    public double getAskQty() {
        return askQty;
    }

}
