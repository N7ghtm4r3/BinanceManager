package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginProperties;

/**
 *  The {@code MarginAsset} class is useful to format Binance Get All Margin Assets request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-all-margin-assets-market_data">https://binance-docs.github.io/apidocs/spot/en/#get-all-margin-assets-market_data</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class MarginAsset {

    private final String assetFullName;
    private final String assetName;
    private boolean isBorrowable;
    private boolean isMortgageable;
    private double userMinBorrow;
    private double userMinRepay;

    public MarginAsset(String assetFullName, String assetName, boolean isBorrowable, boolean isMortgageable,
                       double userMinBorrow, double userMinRepay) {
        this.assetFullName = assetFullName;
        this.assetName = assetName;
        this.isBorrowable = isBorrowable;
        this.isMortgageable = isMortgageable;
        this.userMinBorrow = userMinBorrow;
        this.userMinRepay = userMinRepay;
    }

    public String getAssetFullName() {
        return assetFullName;
    }

    public String getAssetName() {
        return assetName;
    }

    public boolean isBorrowable() {
        return isBorrowable;
    }

    public void setBorrowable(boolean borrowable) {
        isBorrowable = borrowable;
    }

    public boolean isMortgageable() {
        return isMortgageable;
    }

    public void setMortgageable(boolean mortgageable) {
        isMortgageable = mortgageable;
    }

    public double getUserMinBorrow() {
        return userMinBorrow;
    }

    public void setUserMinBorrow(double userMinBorrow) {
        this.userMinBorrow = userMinBorrow;
    }

    public double getUserMinRepay() {
        return userMinRepay;
    }

    public void setUserMinRepay(double userMinRepay) {
        this.userMinRepay = userMinRepay;
    }

}
