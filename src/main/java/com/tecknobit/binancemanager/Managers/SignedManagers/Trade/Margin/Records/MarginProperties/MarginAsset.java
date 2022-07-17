package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginProperties;

/**
 *  The {@code MarginAsset} class is useful to format Binance Get All Margin Assets request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-all-margin-assets-market_data">
 *      https://binance-docs.github.io/apidocs/spot/en/#get-all-margin-assets-market_data</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

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
        if(userMinBorrow < 0)
            throw new IllegalArgumentException("Min borrow quote value cannot be less than 0");
        else
            this.userMinBorrow = userMinBorrow;
        if(userMinRepay < 0)
            throw new IllegalArgumentException("Min repay quote value cannot be less than 0");
        else
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

    /** Method to set {@link #userMinBorrow}
     * @param userMinBorrow: min borrow quote
     * @throws IllegalArgumentException when min borrow quote value is less than 0
     * **/
    public void setUserMinBorrow(double userMinBorrow) {
        if(userMinBorrow < 0)
            throw new IllegalArgumentException("Min borrow quote value cannot be less than 0");
        this.userMinBorrow = userMinBorrow;
    }

    public double getUserMinRepay() {
        return userMinRepay;
    }

    /** Method to set {@link #userMinRepay}
     * @param userMinRepay: min repay quote
     * @throws IllegalArgumentException when min repay quote value is less than 0
     * **/
    public void setUserMinRepay(double userMinRepay) {
        if(userMinRepay < 0)
            throw new IllegalArgumentException("Min repay quote value cannot be less than 0");
        this.userMinRepay = userMinRepay;
    }

}
