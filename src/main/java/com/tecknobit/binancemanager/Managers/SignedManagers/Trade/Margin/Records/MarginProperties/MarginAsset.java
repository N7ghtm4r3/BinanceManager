package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginProperties;

import org.json.JSONObject;

import static com.tecknobit.apimanager.Tools.Trading.TradingTools.roundValue;

/**
 * The {@code MarginAsset} class is useful to format Binance Get All Margin Assets request
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-all-margin-assets-market_data">
 * https://binance-docs.github.io/apidocs/spot/en/#get-all-margin-assets-market_data</a>
 **/

public class MarginAsset {

    /**
     * {@code assetFullName} is instance that memorizes asset full name
     * **/
    private final String assetFullName;

    /**
     * {@code assetName} is instance that memorizes asset name
     * **/
    private final String assetName;

    /**
     * {@code isBorrowable} is instance that memorizes if is borrowable
     * **/
    private boolean isBorrowable;

    /**
     * {@code isMortgageable} is instance that memorizes if is mortgageable
     * **/
    private boolean isMortgageable;

    /**
     * {@code userMinBorrow} is instance that memorizes user min repay quote
     * **/
    private double userMinBorrow;

    /**
     * {@code userMinRepay} is instance that memorizes user min repay quote
     * **/
    private double userMinRepay;

    /** Constructor to init {@link MarginAsset} object
     * @param assetFullName: asset full name
     * @param assetName: asset name
     * @param isBorrowable: is borrowable
     * @param isMortgageable: is mortgageable
     * @param userMinBorrow: min borrow quote
     * @param userMinRepay: min repay quote
     * @throws IllegalArgumentException if parameters range is not respected
     * **/
    public MarginAsset(String assetFullName, String assetName, boolean isBorrowable, boolean isMortgageable,
                       double userMinBorrow, double userMinRepay) {
        this.assetFullName = assetFullName;
        this.assetName = assetName;
        this.isBorrowable = isBorrowable;
        this.isMortgageable = isMortgageable;
        if (userMinBorrow < 0)
            throw new IllegalArgumentException("Min borrow quote value cannot be less than 0");
        else
            this.userMinBorrow = userMinBorrow;
        if (userMinRepay < 0)
            throw new IllegalArgumentException("Min repay quote value cannot be less than 0");
        else
            this.userMinRepay = userMinRepay;
    }

    /**
     * Constructor to init {@link MarginAsset} object
     *
     * @param marginAsset: margin asset details as {@link JSONObject}
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public MarginAsset(JSONObject marginAsset) {
        assetFullName = marginAsset.getString("assetFullName");
        assetName = marginAsset.getString("assetName");
        isBorrowable = marginAsset.getBoolean("isBorrowable");
        isMortgageable = marginAsset.getBoolean("isMortgageable");
        userMinBorrow = marginAsset.getDouble("userMinBorrow");
        if (userMinBorrow < 0)
            throw new IllegalArgumentException("Min borrow quote value cannot be less than 0");
        userMinRepay = marginAsset.getDouble("userMinRepay");
        if (userMinRepay < 0)
            throw new IllegalArgumentException("Min repay quote value cannot be less than 0");
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

    /**
     * Method to get {@link #userMinBorrow} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #userMinBorrow} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getUserMinBorrow(int decimals) {
        return roundValue(userMinBorrow, decimals);
    }

    /**
     * Method to set {@link #userMinBorrow}
     *
     * @param userMinBorrow: min borrow quote
     * @throws IllegalArgumentException when min borrow quote value is less than 0
     **/
    public void setUserMinBorrow(double userMinBorrow) {
        if (userMinBorrow < 0)
            throw new IllegalArgumentException("Min borrow quote value cannot be less than 0");
        this.userMinBorrow = userMinBorrow;
    }

    public double getUserMinRepay() {
        return userMinRepay;
    }

    /**
     * Method to get {@link #userMinRepay} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #userMinRepay} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getUserMinRepay(int decimals) {
        return roundValue(userMinRepay, decimals);
    }

    /**
     * Method to set {@link #userMinRepay}
     *
     * @param userMinRepay: min repay quote
     * @throws IllegalArgumentException when min repay quote value is less than 0
     **/
    public void setUserMinRepay(double userMinRepay) {
        if (userMinRepay < 0)
            throw new IllegalArgumentException("Min repay quote value cannot be less than 0");
        this.userMinRepay = userMinRepay;
    }

    @Override
    public String toString() {
        return "MarginAsset{" +
                "assetFullName='" + assetFullName + '\'' +
                ", assetName='" + assetName + '\'' +
                ", isBorrowable=" + isBorrowable +
                ", isMortgageable=" + isMortgageable +
                ", userMinBorrow=" + userMinBorrow +
                ", userMinRepay=" + userMinRepay +
                '}';
    }

}
