package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common;

/**
 *  The {@code FillSpot} class is useful to obtain and format FillSpot object
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#introduction
 * **/

public class Fill {

    private final double price;
    private final double qty;
    private final double commission;
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

    public double getQty() {
        return qty;
    }

    public double getCommission() {
        return commission;
    }

    public String getCommissionAsset() {
        return commissionAsset;
    }

}
