package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.account;

import com.tecknobit.apimanager.formatters.JsonHelper;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code MarginAccount} class is useful to format {@code "Binance"} Margin Account
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data</a>
 **/

public class MarginAccount {

    /**
     * {@code totalAssetOfBtc} is instance that memorizes total asset of Bitcoin
     **/
    protected double totalAssetOfBtc;

    /**
     * {@code hMarginAccount} is instance useful to work with margin account details in JSON format
     **/
    protected final JsonHelper hMarginAccount;
    /**
     * {@code totalLiabilityOfBtc} is instance that memorizes total liability of Bitcoin
     **/
    protected double totalLiabilityOfBtc;
    /**
     * {@code totalLiabilityOfBtc} is instance that memorizes total net asset of Bitcoin
     **/
    protected double totalNetAssetOfBtc;

    /**
     * Constructor to init {@link MarginAccount} object
     *
     * @param totalAssetOfBtc:     total asset of Bitcoin
     * @param totalLiabilityOfBtc: total liability of Bitcoin
     * @param totalNetAssetOfBtc:  total net asset of Bitcoin
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public MarginAccount(double totalAssetOfBtc, double totalLiabilityOfBtc, double totalNetAssetOfBtc) {
        if (totalAssetOfBtc < 0)
            throw new IllegalArgumentException("Total asset of BTC value cannot be less than 0");
        else
            this.totalAssetOfBtc = totalAssetOfBtc;
        if (totalLiabilityOfBtc < 0)
            throw new IllegalArgumentException("Total liability asset of BTC value cannot be less than 0");
        else
            this.totalLiabilityOfBtc = totalLiabilityOfBtc;
        if (totalNetAssetOfBtc < 0)
            throw new IllegalArgumentException("Total net asset of BTC value cannot be less than 0");
        else
            this.totalNetAssetOfBtc = totalNetAssetOfBtc;
        hMarginAccount = null;
    }

    /**
     * Constructor to init {@link MarginAccount} object
     *
     * @param marginAccount: margin account details as {@link JSONObject}
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public MarginAccount(JSONObject marginAccount) {
        hMarginAccount = new JsonHelper(marginAccount);
        totalAssetOfBtc = hMarginAccount.getDouble("totalAssetOfBtc", 0);
        if (totalAssetOfBtc < 0)
            throw new IllegalArgumentException("Total asset of BTC value cannot be less than 0");
        totalLiabilityOfBtc = hMarginAccount.getDouble("totalLiabilityOfBtc", 0);
        if (totalLiabilityOfBtc < 0)
            throw new IllegalArgumentException("Total liability asset of BTC value cannot be less than 0");
        totalNetAssetOfBtc = hMarginAccount.getDouble("totalNetAssetOfBtc", 0);
        if (totalNetAssetOfBtc < 0)
            throw new IllegalArgumentException("Total net asset of BTC value cannot be less than 0");
    }

    public double getTotalAssetOfBtc() {
        return totalAssetOfBtc;
    }

    /**
     * Method to set {@link #totalAssetOfBtc}
     *
     * @param totalAssetOfBtc: total asset of Bitcoin
     * @throws IllegalArgumentException when number value is less than 0
     **/
    public void setTotalAssetOfBtc(double totalAssetOfBtc) {
        if (totalAssetOfBtc < 0)
            throw new IllegalArgumentException("Total asset of BTC value cannot be less than 0");
        this.totalAssetOfBtc = totalAssetOfBtc;
    }

    /**
     * Method to get {@link #totalAssetOfBtc} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalAssetOfBtc} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTotalAssetOfBtc(int decimals) {
        return roundValue(totalAssetOfBtc, decimals);
    }

    public double getTotalLiabilityOfBtc() {
        return totalLiabilityOfBtc;
    }

    /**
     * Method to get {@link #totalLiabilityOfBtc} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalLiabilityOfBtc} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTotalLiabilityOfBtc(int decimals) {
        return roundValue(totalLiabilityOfBtc, decimals);
    }

    /**
     * Method to set {@link #totalLiabilityOfBtc}
     *
     * @param totalLiabilityOfBtc: total liability of Bitcoin
     * @throws IllegalArgumentException when number value is less than 0
     **/
    public void setTotalLiabilityOfBtc(double totalLiabilityOfBtc) {
        if (totalLiabilityOfBtc < 0)
            throw new IllegalArgumentException("Total liability asset of BTC value cannot be less than 0");
        this.totalLiabilityOfBtc = totalLiabilityOfBtc;
    }

    public double getTotalNetAssetOfBtc() {
        return totalNetAssetOfBtc;
    }

    /**
     * Method to get {@link #totalNetAssetOfBtc} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalNetAssetOfBtc} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTotalNetAssetOfBtc(int decimals) {
        return roundValue(totalNetAssetOfBtc, decimals);
    }

    /**
     * Method to set {@link #totalNetAssetOfBtc}
     *
     * @param totalNetAssetOfBtc: total net asset of Bitcoin
     * @throws IllegalArgumentException when number value is less than 0
     **/
    public void setTotalNetAssetOfBtc(double totalNetAssetOfBtc) {
        if (totalNetAssetOfBtc < 0)
            throw new IllegalArgumentException("Total net asset of BTC value cannot be less than 0");
        this.totalNetAssetOfBtc = totalNetAssetOfBtc;
    }

    @Override
    public String toString() {
        return "MarginAccount{" +
                "totalAssetOfBtc=" + totalAssetOfBtc +
                ", totalLiabilityOfBtc=" + totalLiabilityOfBtc +
                ", totalNetAssetOfBtc=" + totalNetAssetOfBtc +
                '}';
    }

}
