package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Account;

/**
 * The {@code IsolatedMarginAsset} class is useful to format IsolatedMarginAsset object of Binance Isolated Margin Asset
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data</a>
 * **/

public class IsolatedMarginAsset {

    /**
     * {@code asset} is instance that memorizes asset
     * **/
    private final String asset;

    /**
     * {@code borrowEnabled} is instance if borrow is enabled for asset
     * **/
    private boolean borrowEnabled;

    /**
     * {@code borrowed} is instance that memorizes amount of borrow from asset
     * **/
    private double borrowed;

    /**
     * {@code free} is instance that memorizes free amount of asset
     * **/
    private double free;

    /**
     * {@code interest} is instance that memorizes amount of interest in asset
     * **/
    private double interest;

    /**
     * {@code locked} is instance that memorizes amount locked for asset
     * **/
    private double locked;

    /**
     * {@code netAsset} is instance that memorizes net asset
     * **/
    private double netAsset;

    /**
     * {@code netAssetOfBtc} is instance that memorizes net asset of Bitcoin
     * **/
    private double netAssetOfBtc;

    /**
     * {@code repayEnabled} is instance if repay is enabled for asset
     * **/
    private boolean repayEnabled;

    /**
     * {@code totalAsset} is instance that memorizes total asset amount
     * **/
    private double totalAsset;

    /** Constructor to init {@link IsolatedMarginAsset} object
     * @param asset: asset
     * @param borrowEnabled: borrow is enabled for asset
     * @param borrowed: amount of borrow from asset
     * @param free: free amount of asset
     * @param interest: amount of interest in asset
     * @param locked: amount locked for asset
     * @param netAsset: net asset
     * @param netAssetOfBtc: net asset of Bitcoin
     * @param repayEnabled: repay is enabled for asset
     * @param totalAsset: total asset amount
     * @throws IllegalArgumentException if parameters range is not respected
     * **/
    public IsolatedMarginAsset(String asset, boolean borrowEnabled, double borrowed, double free, double interest,
                               double locked, double netAsset, double netAssetOfBtc, boolean repayEnabled, double totalAsset) {
        this.asset = asset;
        this.borrowEnabled = borrowEnabled;
        if(borrowed < 0)
            throw new IllegalArgumentException("Borrowed value cannot be less than 0");
        else
            this.borrowed = borrowed;
        if(free < 0)
            throw new IllegalArgumentException("Free value cannot be less than 0");
        else
            this.free = free;
        if(interest < 0)
            throw new IllegalArgumentException("Interest value cannot be less than 0");
        else
            this.interest = interest;
        if(locked < 0)
            throw new IllegalArgumentException("Locked value cannot be less than 0");
        else
            this.locked = locked;
        if(netAsset < 0)
            throw new IllegalArgumentException("Net asset value cannot be less than 0");
        else
            this.netAsset = netAsset;
        if(netAssetOfBtc < 0)
            throw new IllegalArgumentException("Net asset of BTC value cannot be less than 0");
        else
            this.netAssetOfBtc = netAssetOfBtc;
        this.repayEnabled = repayEnabled;
        if(totalAsset < 0)
            throw new IllegalArgumentException("Total asset value cannot be less than 0");
        else
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

    /** Method to set {@link #borrowed}
     * @param borrowed: amount of borrow from asset
     * @throws IllegalArgumentException when borrowed is less than 0
     * **/
    public void setBorrowed(double borrowed) {
        if(borrowed < 0)
            throw new IllegalArgumentException("Borrowed value cannot be less than 0");
        this.borrowed = borrowed;
    }

    public double getFree() {
        return free;
    }

    /** Method to set {@link #free}
     * @param free: free amount of asset
     * @throws IllegalArgumentException when free value is less than 0
     * **/
    public void setFree(double free) {
        if(free < 0)
            throw new IllegalArgumentException("Free value cannot be less than 0");
        this.free = free;
    }

    public double getInterest() {
        return interest;
    }

    /** Method to set {@link #interest}
     * @param interest: amount of interest in asset
     * @throws IllegalArgumentException when interest value is less than 0
     * **/
    public void setInterest(double interest) {
        if(interest < 0)
            throw new IllegalArgumentException("Interest value cannot be less than 0");
        this.interest = interest;
    }

    public double getLocked() {
        return locked;
    }

    /** Method to set {@link #locked}
     * @param locked: amount locked for asset
     * @throws IllegalArgumentException when amount locked value is less than 0
     * **/
    public void setLocked(double locked) {
        if(locked < 0)
            throw new IllegalArgumentException("Locked value cannot be less than 0");
        this.locked = locked;
    }

    public double getNetAsset() {
        return netAsset;
    }

    /** Method to set {@link #netAsset}
     * @param netAsset: net asset
     * @throws IllegalArgumentException when net asset value is less than 0
     * **/
    public void setNetAsset(double netAsset) {
        if(netAsset < 0)
            throw new IllegalArgumentException("Net asset value cannot be less than 0");
        this.netAsset = netAsset;
    }

    public double getNetAssetOfBtc() {
        return netAssetOfBtc;
    }

    /** Method to set {@link #netAssetOfBtc}
     * @param netAssetOfBtc: net asset of Bitcoin
     * @throws IllegalArgumentException when net asset of Bitcoin value is less than 0
     * **/
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

    /** Method to set {@link #totalAsset}
     * @param totalAsset: total asset amount
     * @throws IllegalArgumentException when total asset amount value is less than 0
     * **/
    public void setTotalAsset(double totalAsset) {
        if(totalAsset < 0)
            throw new IllegalArgumentException("Total asset value cannot be less than 0");
        this.totalAsset = totalAsset;
    }

}
