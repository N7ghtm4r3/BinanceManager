package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.asset;

import com.tecknobit.apimanager.formatters.JsonHelper;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code UserAsset} class is useful to format a {@code "Binance"}'s user asset
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#user-asset-user_data">
 * User Asset (USER_DATA)</a>
 **/
public class UserAsset {

    /**
     * {@code asset} asset value
     **/
    private final String asset;

    /**
     * {@code free} free value
     **/
    private double free;

    /**
     * {@code locked} locked value
     **/
    private double locked;

    /**
     * {@code freeze} freeze value
     **/
    private double freeze;

    /**
     * {@code withdrawing} withdrawing value
     **/
    private double withdrawing;

    /**
     * {@code ipoable} ipoable value
     **/
    private double ipoable;

    /**
     * {@code btcValuation} btc valuation value
     **/
    private double btcValuation;

    /**
     * Constructor to init {@link UserAsset} object
     *
     * @param asset:        asset value
     * @param free:         free value
     * @param locked:       locked value
     * @param freeze:       freeze value
     * @param withdrawing:  withdrawing value
     * @param ipoable:      ipoable value
     * @param btcValuation: btc valuation value
     **/
    public UserAsset(String asset, double free, double locked, double freeze, double withdrawing, double ipoable,
                     double btcValuation) {
        this.asset = asset;
        this.free = free;
        this.locked = locked;
        this.freeze = freeze;
        this.withdrawing = withdrawing;
        this.ipoable = ipoable;
        this.btcValuation = btcValuation;
    }

    /**
     * Constructor to init {@link UserAsset} object
     *
     * @param jAsset: user asset details as {@link JSONObject}
     **/
    public UserAsset(JSONObject jAsset) {
        JsonHelper hAsset = new JsonHelper(jAsset);
        this.asset = hAsset.getString("asset");
        this.free = hAsset.getDouble("free", 0);
        this.locked = hAsset.getDouble("locked", 0);
        this.freeze = hAsset.getDouble("freeze", 0);
        this.withdrawing = hAsset.getDouble("withdrawing", 0);
        this.ipoable = hAsset.getDouble("ipoable", 0);
        this.btcValuation = hAsset.getDouble("btcValuation", 0);
    }

    /**
     * Method to get {@link #asset} instance <br>
     * Any params required
     *
     * @return {@link #asset} instance as {@link String}
     **/
    public String getAsset() {
        return asset;
    }

    /**
     * Method to get {@link #free} instance <br>
     * Any params required
     *
     * @return {@link #free} instance as double
     **/
    public double getFree() {
        return free;
    }

    /**
     * Method to set {@link #free}
     *
     * @param free: maker commission
     **/
    public void setFree(double free) {
        this.free = free;
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
     * Method to get {@link #locked} instance <br>
     * Any params required
     *
     * @return {@link #locked} instance as double
     **/
    public double getLocked() {
        return locked;
    }

    /**
     * Method to set {@link #locked}
     *
     * @param locked: maker commission
     **/
    public void setLocked(double locked) {
        this.locked = locked;
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
     * Method to get {@link #freeze} instance <br>
     * Any params required
     *
     * @return {@link #freeze} instance as double
     **/
    public double getFreeze() {
        return freeze;
    }

    /**
     * Method to set {@link #freeze}
     *
     * @param freeze: maker commission
     **/
    public void setFreeze(double freeze) {
        this.freeze = freeze;
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
     * Method to get {@link #withdrawing} instance <br>
     * Any params required
     *
     * @return {@link #withdrawing} instance as double
     **/
    public double getWithdrawing() {
        return withdrawing;
    }

    /**
     * Method to set {@link #withdrawing}
     *
     * @param withdrawing: maker commission
     **/
    public void setWithdrawing(double withdrawing) {
        this.withdrawing = withdrawing;
    }

    /**
     * Method to get {@link #withdrawing} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #withdrawing} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getWithdrawing(int decimals) {
        return roundValue(withdrawing, decimals);
    }

    /**
     * Method to get {@link #ipoable} instance <br>
     * Any params required
     *
     * @return {@link #ipoable} instance as double
     **/
    public double getIpoable() {
        return ipoable;
    }

    /**
     * Method to set {@link #ipoable}
     *
     * @param ipoable: maker commission
     **/
    public void setIpoable(double ipoable) {
        this.ipoable = ipoable;
    }

    /**
     * Method to get {@link #ipoable} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #ipoable} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getIpoable(int decimals) {
        return roundValue(ipoable, decimals);
    }

    /**
     * Method to get {@link #btcValuation} instance <br>
     * Any params required
     *
     * @return {@link #btcValuation} instance as double
     **/
    public double getBtcValuation() {
        return btcValuation;
    }

    /**
     * Method to set {@link #btcValuation}
     *
     * @param btcValuation: maker commission
     **/
    public void setBtcValuation(double btcValuation) {
        this.btcValuation = btcValuation;
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
