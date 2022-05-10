package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common;

/**
 *  The {@code FillSpot} class is useful to obtain and format FillSpot object
 *  @implNote used by BinanceSpotManager, BinanceMarginManager
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#introduction">https://binance-docs.github.io/apidocs/spot/en/#introduction</a>
 * **/

public class Fill {

    private double price;
    private double qty;
    private double commission;
    private final String commissionAsset;

    public Fill(double price, double qty, double commission, String commissionAsset) {
        this.price = price;
        this.qty = qty;
        this.commission = commission;
        this.commissionAsset = commissionAsset;
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

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        if(commission < 0)
            throw new IllegalArgumentException("Commission value cannot be less than 0");
        this.commission = commission;
    }

    public String getCommissionAsset() {
        return commissionAsset;
    }

}
