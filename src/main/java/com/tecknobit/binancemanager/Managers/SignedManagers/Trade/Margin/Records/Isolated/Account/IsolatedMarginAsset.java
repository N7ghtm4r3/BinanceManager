package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Account;

import com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.AccountSnapshots.AccountSnapshotMargin;

/**
 * The {@code IsolatedMarginAsset} class is useful to format IsolatedMarginAsset object of Binance Isolated Margin Asset
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data</a>
 * **/

public class IsolatedMarginAsset extends AccountSnapshotMargin.UserAssetMargin {

    /**
     * {@code borrowEnabled} is instance if borrow is enabled for asset
     * **/
    private boolean borrowEnabled;

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
        super(asset, borrowed, free, interest, locked, netAsset);
        this.borrowEnabled = borrowEnabled;
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

    public boolean isBorrowEnabled() {
        return borrowEnabled;
    }

    public void setBorrowEnabled(boolean borrowEnabled) {
        this.borrowEnabled = borrowEnabled;
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

    @Override
    public String toString() {
        return "IsolatedMarginAsset{" +
                "borrowEnabled=" + borrowEnabled +
                ", netAssetOfBtc=" + netAssetOfBtc +
                ", repayEnabled=" + repayEnabled +
                ", totalAsset=" + totalAsset +
                ", borrowed=" + borrowed +
                ", interest=" + interest +
                ", netAsset=" + netAsset +
                ", asset='" + asset + '\'' +
                ", free=" + free +
                ", locked=" + locked +
                '}';
    }

}
