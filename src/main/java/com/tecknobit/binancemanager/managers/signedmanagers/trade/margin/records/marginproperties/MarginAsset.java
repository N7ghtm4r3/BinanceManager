package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.marginproperties;

import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code MarginAsset} class is useful to format a {@code "Binance"}'s margin asset
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-all-margin-assets-market_data">
 * Get All Margin Assets (MARKET_DATA)</a>
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
     **/
    private final boolean isBorrowable;

    /**
     * {@code isMortgageable} is instance that memorizes if is mortgageable
     **/
    private final boolean isMortgageable;

    /**
     * {@code userMinBorrow} is instance that memorizes user min repay quote
     **/
    private final double userMinBorrow;

    /**
     * {@code userMinRepay} is instance that memorizes user min repay quote
     **/
    private final double userMinRepay;

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

    /**
     * Method to get {@link #assetFullName} instance <br>
     * Any params required
     *
     * @return {@link #assetFullName} instance as {@link String}
     **/
    public String getAssetFullName() {
        return assetFullName;
    }

    /**
     * Method to get {@link #assetName} instance <br>
     * Any params required
     *
     * @return {@link #assetName} instance as {@link String}
     **/
    public String getAssetName() {
        return assetName;
    }

    /**
     * Method to get {@link #isBorrowable} instance <br>
     * Any params required
     *
     * @return {@link #isBorrowable} instance as boolean
     **/
    public boolean isBorrowable() {
        return isBorrowable;
    }

    /**
     * Method to get {@link #isMortgageable} instance <br>
     * Any params required
     *
     * @return {@link #isMortgageable} instance as boolean
     **/
    public boolean isMortgageable() {
        return isMortgageable;
    }

    /**
     * Method to get {@link #userMinBorrow} instance <br>
     * Any params required
     *
     * @return {@link #userMinBorrow} instance as double
     **/
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
     * Method to get {@link #userMinRepay} instance <br>
     * Any params required
     *
     * @return {@link #userMinRepay} instance as double
     **/
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
     * Returns a string representation of the object <br>
     * Any params required
     *
     * @return a string representation of the object as {@link String}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

}
