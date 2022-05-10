package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Account;

/**
 * The {@code MarginAccountTrade} class is useful to format Binance Margin Account Trade request response
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-isolated-margin-account-trade">https://binance-docs.github.io/apidocs/spot/en/#enable-isolated-margin-account-trade</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class MarginAccountTrade {

    private double commission;
    private double commissionAsset;
    private final long id;
    private boolean isBestMatch;
    private boolean isBuyer;
    private boolean isMaker;
    private final long orderId;
    private double price;
    private double qty;
    private final String symbol;
    private boolean isIsolated;
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

    public void setCommission(double commission) {
        if(commissionAsset < 0)
            throw new IllegalArgumentException("Commission value cannot be less than 0");
        this.commission = commission;
    }

    public double getCommissionAsset() {
        return commissionAsset;
    }

    public void setCommissionAsset(double commissionAsset) {
        if(commissionAsset < 0)
            throw new IllegalArgumentException("Commission asset value cannot be less than 0");
        this.commissionAsset = commissionAsset;
    }

    public long getId() {
        return id;
    }

    public boolean isBestMatch() {
        return isBestMatch;
    }

    public void setBestMatch(boolean bestMatch) {
        isBestMatch = bestMatch;
    }

    public boolean isBuyer() {
        return isBuyer;
    }

    public void setBuyer(boolean buyer) {
        isBuyer = buyer;
    }

    public boolean isMaker() {
        return isMaker;
    }

    public void setMaker(boolean maker) {
        isMaker = maker;
    }

    public long getOrderId() {
        return orderId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if(price < 0)
            throw new IllegalArgumentException("Price value cannot be less than 0");
        this.price = price;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        if(qty < 0)
            throw new IllegalArgumentException("Quantity value cannot be less than 0");
        this.qty = qty;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean isIsolated() {
        return isIsolated;
    }

    public void setIsolated(boolean isolated) {
        isIsolated = isolated;
    }

    public long getTime() {
        return time;
    }

}
