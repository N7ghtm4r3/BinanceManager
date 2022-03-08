package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Account;

public class MarginAccountTrade {

    private final double commission;
    private final double commissionAsset;
    private final long id;
    private final boolean isBestMatch;
    private final boolean isBuyer;
    private final boolean isMaker;
    private final long orderId;
    private final double price;
    private final double qty;
    private final String symbol;
    private final boolean isIsolated;
    private final long time;


    public MarginAccountTrade(double commission, double commissionAsset, long id, boolean isBestMatch, boolean isBuyer,
                              boolean isMaker, long orderId, double price, double qty, String symbol, boolean isIsolated,
                              long time) {
        this.commission = commission;
        this.commissionAsset = commissionAsset;
        this.id = id;
        this.isBestMatch = isBestMatch;
        this.isBuyer = isBuyer;
        this.isMaker = isMaker;
        this.orderId = orderId;
        this.price = price;
        this.qty = qty;
        this.symbol = symbol;
        this.isIsolated = isIsolated;
        this.time = time;
    }

    public double getCommission() {
        return commission;
    }

    public double getCommissionAsset() {
        return commissionAsset;
    }

    public long getId() {
        return id;
    }

    public boolean isBestMatch() {
        return isBestMatch;
    }

    public boolean isBuyer() {
        return isBuyer;
    }

    public boolean isMaker() {
        return isMaker;
    }

    public long getOrderId() {
        return orderId;
    }

    public double getPrice() {
        return price;
    }

    public double getQty() {
        return qty;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean isIsolated() {
        return isIsolated;
    }

    public long getTime() {
        return time;
    }

}
