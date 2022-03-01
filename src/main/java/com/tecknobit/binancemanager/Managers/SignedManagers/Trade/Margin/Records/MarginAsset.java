package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records;

public class MarginAsset {

    private final String assetFullName;
    private final String assetName;
    private final boolean isBorrowable;
    private final boolean isMortgageable;
    private final double userMinBorrow;
    private final double userMinRepay;

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

    public boolean isMortgageable() {
        return isMortgageable;
    }

    public double getUserMinBorrow() {
        return userMinBorrow;
    }

    public double getUserMinRepay() {
        return userMinRepay;
    }

}
