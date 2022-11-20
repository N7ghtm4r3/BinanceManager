package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records;

import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code FundingWallet} class is useful to manage FundingWallet {@code "Binance"} request
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#funding-wallet-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#funding-wallet-user_data</a>
 **/

public class FundingWallet {

    /**
     * {@code asset} is instance that memorizes asset value
     * **/
    private final String asset;

    /**
     * {@code free} is instance that memorizes free value
     * **/
    private double free;

    /**
     * {@code locked} is instance that memorizes locked value
     * **/
    private double locked;

    /**
     * {@code freeze} is instance that memorizes freeze value
     * **/
    private double freeze;

    /**
     * {@code withdrawing} is instance that memorizes withdrawing value
     * **/
    private int withdrawing;

    /**
     * {@code btcValuation} is instance that memorizes bitcoin valuation value
     * **/
    private double btcValuation;

    /** Constructor to init {@link FundingWallet} object
     * @param asset: asset value
     * @param free: free value
     * @param locked: locked value
     * @param freeze: freeze value
     * @param withdrawing: withdrawing value
     * @param btcValuation: Bitcoin valuation value
     * @throws IllegalArgumentException if parameters range is not respected
     * **/
    public FundingWallet(String asset, double free, double locked, double freeze, int withdrawing, double btcValuation) {
        this.asset = asset;
        if(free < 0)
            throw new IllegalArgumentException("Free value cannot be less than 0");
        else
            this.free = free;
        if(locked < 0)
            throw new IllegalArgumentException("Locked value cannot be less than 0");
        else
            this.locked = locked;
        if (freeze < 0)
            throw new IllegalArgumentException("Freeze value cannot be less than 0");
        else
            this.freeze = freeze;
        if (withdrawing < 0)
            throw new IllegalArgumentException("Withdrawing value cannot be less than 0");
        else
            this.withdrawing = withdrawing;
        if (btcValuation < 0)
            throw new IllegalArgumentException("BTC valuation value cannot be less than 0");
        else
            this.btcValuation = btcValuation;
    }

    /**
     * Constructor to init {@link FundingWallet} object
     *
     * @param fundingWallet: funding wallet details as {@link JSONObject}
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public FundingWallet(JSONObject fundingWallet) {
        asset = fundingWallet.getString("asset");
        free = fundingWallet.getDouble("free");
        if (free < 0)
            throw new IllegalArgumentException("Free value cannot be less than 0");
        locked = fundingWallet.getDouble("locked");
        if (locked < 0)
            throw new IllegalArgumentException("Locked value cannot be less than 0");
        freeze = fundingWallet.getDouble("freeze");
        if (freeze < 0)
            throw new IllegalArgumentException("Freeze value cannot be less than 0");
        withdrawing = fundingWallet.getInt("withdrawing");
        if (withdrawing < 0)
            throw new IllegalArgumentException("Withdrawing value cannot be less than 0");
        btcValuation = fundingWallet.getDouble("btcValuation");
        if (btcValuation < 0)
            throw new IllegalArgumentException("BTC valuation value cannot be less than 0");
    }

    public String getAsset() {
        return asset;
    }

    public double getFree() {
        return free;
    }

    /**
     * Method to get {@link #free} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #free} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getFree(int decimals) {
        return roundValue(free, decimals);
    }

    /**
     * Method to set {@link #free}
     *
     * @param free: free value
     * @throws IllegalArgumentException when free value is less than 0
     **/
    public void setFree(double free) {
        if (free < 0)
            throw new IllegalArgumentException("Free value cannot be less than 0");
        this.free = free;
    }

    public double getLocked() {
        return locked;
    }

    /**
     * Method to get {@link #locked} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #locked} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getLocked(int decimals) {
        return roundValue(locked, decimals);
    }

    /**
     * Method to set {@link #locked}
     *
     * @param locked: locked value
     * @throws IllegalArgumentException when locked value is less than 0
     **/
    public void setLocked(double locked) {
        if (locked < 0)
            throw new IllegalArgumentException("Locked value cannot be less than 0");
        this.locked = locked;
    }

    public double getFreeze() {
        return freeze;
    }

    /**
     * Method to get {@link #freeze} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #freeze} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getFreeze(int decimals) {
        return roundValue(freeze, decimals);
    }

    /**
     * Method to set {@link #freeze}
     *
     * @param freeze: freeze value
     * @throws IllegalArgumentException when freeze value is less than 0
     **/
    public void setFreeze(double freeze) {
        if (freeze < 0)
            throw new IllegalArgumentException("Freeze value cannot be less than 0");
        this.freeze = freeze;
    }

    public int getWithdrawing() {
        return withdrawing;
    }

    /** Method to set {@link #withdrawing}
     * @param withdrawing: withdrawing value
     * @throws IllegalArgumentException when withdrawing value is less than 0
     * **/
    public void setWithdrawing(int withdrawing) {
        if (withdrawing < 0)
            throw new IllegalArgumentException("Withdrawing value cannot be less than 0");
        this.withdrawing = withdrawing;
    }

    public double getBtcValuation() {
        return btcValuation;
    }

    /**
     * Method to get {@link #btcValuation} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #btcValuation} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getBtcValuation(int decimals) {
        return roundValue(btcValuation, decimals);
    }

    /**
     * Method to set {@link #btcValuation}
     *
     * @param btcValuation: Bitcoin valuation value
     * @throws IllegalArgumentException when Bitcoin valuation value is less than 0
     **/
    public void setBtcValuation(double btcValuation) {
        if (btcValuation < 0)
            throw new IllegalArgumentException("BTC valuation value cannot be less than 0");
        this.btcValuation = btcValuation;
    }

    @Override
    public String toString() {
        return "FundingWallet{" +
                "asset='" + asset + '\'' +
                ", free=" + free +
                ", locked=" + locked +
                ", freeze=" + freeze +
                ", withdrawing=" + withdrawing +
                ", btcValuation=" + btcValuation +
                '}';
    }

}
