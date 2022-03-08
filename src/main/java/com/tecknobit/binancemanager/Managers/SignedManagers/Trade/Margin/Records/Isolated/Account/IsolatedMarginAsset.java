package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Account;

public class IsolatedMarginAsset {

    private final String asset;
    private final boolean borrowEnabled;
    private final double borrowed;
    private final double free;
    private final double interest;
    private final double locked;
    private final double netAsset;
    private final double netAssetOfBtc;
    private final boolean repayEnabled;
    private final double totalAsset;

    public IsolatedMarginAsset(String asset, boolean borrowEnabled, double borrowed, double free, double interest,
                               double locked, double netAsset, double netAssetOfBtc, boolean repayEnabled, double totalAsset) {
        this.asset = asset;
        this.borrowEnabled = borrowEnabled;
        this.borrowed = borrowed;
        this.free = free;
        this.interest = interest;
        this.locked = locked;
        this.netAsset = netAsset;
        this.netAssetOfBtc = netAssetOfBtc;
        this.repayEnabled = repayEnabled;
        this.totalAsset = totalAsset;
    }

    public String getAsset() {
        return asset;
    }

    public boolean isBorrowEnabled() {
        return borrowEnabled;
    }

    public double getBorrowed() {
        return borrowed;
    }

    public double getFree() {
        return free;
    }

    public double getInterest() {
        return interest;
    }

    public double getLocked() {
        return locked;
    }

    public double getNetAsset() {
        return netAsset;
    }

    public double getNetAssetOfBtc() {
        return netAssetOfBtc;
    }

    public boolean isRepayEnabled() {
        return repayEnabled;
    }

    public double getTotalAsset() {
        return totalAsset;
    }

}
