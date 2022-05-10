package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Account;

/**
 * The {@code IsolatedMarginAsset} class is useful to format IsolatedMarginAsset object of Binance Isolated Margin Asset
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data</a>
 * **/

public class IsolatedMarginAsset {

    private final String asset;
    private boolean borrowEnabled;
    private double borrowed;
    private double free;
    private double interest;
    private double locked;
    private double netAsset;
    private double netAssetOfBtc;
    private boolean repayEnabled;
    private double totalAsset;

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

    public void setBorrowEnabled(boolean borrowEnabled) {
        this.borrowEnabled = borrowEnabled;
    }

    public double getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(double borrowed) {
        if(borrowed < 0)
            throw new IllegalArgumentException("Borrowed value cannot be less than 0");
        this.borrowed = borrowed;
    }

    public double getFree() {
        return free;
    }

    public void setFree(double free) {
        if(free < 0)
            throw new IllegalArgumentException("Free value cannot be less than 0");
        this.free = free;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        if(interest < 0)
            throw new IllegalArgumentException("Interest value cannot be less than 0");
        this.interest = interest;
    }

    public double getLocked() {
        return locked;
    }

    public void setLocked(double locked) {
        if(locked < 0)
            throw new IllegalArgumentException("Locked value cannot be less than 0");
        this.locked = locked;
    }

    public double getNetAsset() {
        return netAsset;
    }

    public void setNetAsset(double netAsset) {
        if(netAsset < 0)
            throw new IllegalArgumentException("Net asset value cannot be less than 0");
        this.netAsset = netAsset;
    }

    public double getNetAssetOfBtc() {
        return netAssetOfBtc;
    }

    public void setNetAssetOfBtc(double netAssetOfBtc) {
        if(netAssetOfBtc < 0)
            throw new IllegalArgumentException("Net asset of BTC value cannot be less than 0");
        this.netAssetOfBtc = netAssetOfBtc;
    }

    public boolean isRepayEnabled() {
        return repayEnabled;
    }

    public void setRepayEnabled(boolean repayEnabled) {
        this.repayEnabled = repayEnabled;
    }

    public double getTotalAsset() {
        return totalAsset;
    }

    public void setTotalAsset(double totalAsset) {
        if(totalAsset < 0)
            throw new IllegalArgumentException("Total asset value cannot be less than 0");
        this.totalAsset = totalAsset;
    }

}
