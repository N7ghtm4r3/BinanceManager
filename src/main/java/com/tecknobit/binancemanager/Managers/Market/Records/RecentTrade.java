package com.tecknobit.binancemanager.Managers.Market.Records;

public class RecentTrade {

    private final long id;
    private final double price;
    private final double qty;
    private final double quoteQty;
    private final long time;
    private final boolean isBuyerMaker;
    private final boolean isBestMatch;

    public RecentTrade(long id, double price, double qty, double quoteQty, long time, boolean isBuyerMaker, boolean isBestMatch) {
        this.id = id;
        this.price = price;
        this.qty = qty;
        this.quoteQty = quoteQty;
        this.time = time;
        this.isBuyerMaker = isBuyerMaker;
        this.isBestMatch = isBestMatch;
    }

    public long getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public double getQty() {
        return qty;
    }

    public double getQuoteQty() {
        return quoteQty;
    }

    public long getTime() {
        return time;
    }

    public boolean isBuyerMaker() {
        return isBuyerMaker;
    }

    public boolean isBestMatch() {
        return isBestMatch;
    }

}
