package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code SimpleEarnAccount} class is useful to format a {@code Binance}'s simple earn account
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#simple-account-user_data">
 * Simple Account (USER_DATA)</a>
 * @see BinanceItem
 */
public class SimpleEarnAccount extends BinanceItem {

    /**
     * {@code totalAmountInBTC} total amount in BTC value
     */
    private final double totalAmountInBTC;

    /**
     * {@code totalAmountInUSDT} total amount in USDT value
     */
    private final double totalAmountInUSDT;

    /**
     * {@code totalFlexibleAmountInBTC} total flexible amount in BTC value
     */
    private final double totalFlexibleAmountInBTC;

    /**
     * {@code totalFlexibleAmountInUSDT} total flexible amount in USDT value
     */
    private final double totalFlexibleAmountInUSDT;

    /**
     * {@code totalLockedInBTC} total locked in BTC value
     */
    private final double totalLockedInBTC;

    /**
     * {@code totalLockedInUSDT} total locked in USDT value
     */
    private final double totalLockedInUSDT;

    /**
     * Constructor to init a {@link SimpleEarnAccount} object
     *
     * @param totalAmountInBTC:          total amount in BTC value
     * @param totalAmountInUSDT:         total amount in USDT value
     * @param totalFlexibleAmountInBTC:  total flexible amount in BTC value
     * @param totalFlexibleAmountInUSDT: total flexible amount in USDT value
     * @param totalLockedInBTC:          total locked in BTC value
     * @param totalLockedInUSDT:         total locked in USDT value
     */
    public SimpleEarnAccount(double totalAmountInBTC, double totalAmountInUSDT, double totalFlexibleAmountInBTC,
                             double totalFlexibleAmountInUSDT, double totalLockedInBTC, double totalLockedInUSDT) {
        super(null);
        this.totalAmountInBTC = totalAmountInBTC;
        this.totalAmountInUSDT = totalAmountInUSDT;
        this.totalFlexibleAmountInBTC = totalFlexibleAmountInBTC;
        this.totalFlexibleAmountInUSDT = totalFlexibleAmountInUSDT;
        this.totalLockedInBTC = totalLockedInBTC;
        this.totalLockedInUSDT = totalLockedInUSDT;
    }

    /**
     * Constructor to init a {@link SimpleEarnAccount} object
     *
     * @param jSimpleEarnAccount: simple earn account details as {@link JSONObject}
     */
    public SimpleEarnAccount(JSONObject jSimpleEarnAccount) {
        super(jSimpleEarnAccount);
        totalAmountInBTC = hItem.getDouble("totalAmountInBTC", 0);
        totalAmountInUSDT = hItem.getDouble("totalAmountInUSDT", 0);
        totalFlexibleAmountInBTC = hItem.getDouble("totalFlexibleAmountInBTC", 0);
        totalFlexibleAmountInUSDT = hItem.getDouble("totalFlexibleAmountInUSDT", 0);
        totalLockedInBTC = hItem.getDouble("totalLockedInBTC", 0);
        totalLockedInUSDT = hItem.getDouble("totalLockedInUSDT", 0);
    }

    /**
     * Method to get {@link #totalAmountInBTC} instance <br>
     * No-any params required
     *
     * @return {@link #totalAmountInBTC} instance as double
     */
    public double getTotalAmountInBTC() {
        return totalAmountInBTC;
    }

    /**
     * Method to get {@link #totalAmountInBTC} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalAmountInBTC} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getTotalAmountInBTC(int decimals) {
        return roundValue(totalAmountInBTC, decimals);
    }

    /**
     * Method to get {@link #totalAmountInUSDT} instance <br>
     * No-any params required
     *
     * @return {@link #totalAmountInUSDT} instance as double
     */
    public double getTotalAmountInUSDT() {
        return totalAmountInUSDT;
    }

    /**
     * Method to get {@link #totalAmountInUSDT} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalAmountInUSDT} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getTotalAmountInUSDT(int decimals) {
        return roundValue(totalAmountInUSDT, decimals);
    }

    /**
     * Method to get {@link #totalFlexibleAmountInBTC} instance <br>
     * No-any params required
     *
     * @return {@link #totalFlexibleAmountInBTC} instance as double
     */
    public double getTotalFlexibleAmountInBTC() {
        return totalFlexibleAmountInBTC;
    }

    /**
     * Method to get {@link #totalFlexibleAmountInBTC} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalFlexibleAmountInBTC} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getTotalFlexibleAmountInBTC(int decimals) {
        return roundValue(totalFlexibleAmountInBTC, decimals);
    }

    /**
     * Method to get {@link #totalFlexibleAmountInUSDT} instance <br>
     * No-any params required
     *
     * @return {@link #totalFlexibleAmountInUSDT} instance as double
     */
    public double getTotalFlexibleAmountInUSDT() {
        return totalFlexibleAmountInUSDT;
    }

    /**
     * Method to get {@link #totalFlexibleAmountInUSDT} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalFlexibleAmountInUSDT} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getTotalFlexibleAmountInUSDT(int decimals) {
        return roundValue(totalFlexibleAmountInUSDT, decimals);
    }

    /**
     * Method to get {@link #totalLockedInBTC} instance <br>
     * No-any params required
     *
     * @return {@link #totalLockedInBTC} instance as double
     */
    public double getTotalLockedInBTC() {
        return totalLockedInBTC;
    }

    /**
     * Method to get {@link #totalLockedInBTC} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalLockedInBTC} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getTotalLockedInBTC(int decimals) {
        return roundValue(totalLockedInBTC, decimals);
    }

    /**
     * Method to get {@link #totalLockedInUSDT} instance <br>
     * No-any params required
     *
     * @return {@link #totalLockedInUSDT} instance as double
     */
    public double getTotalLockedInUSDT() {
        return totalLockedInUSDT;
    }

    /**
     * Method to get {@link #totalLockedInUSDT} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalLockedInUSDT} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getTotalLockedInUSDT(int decimals) {
        return roundValue(totalLockedInUSDT, decimals);
    }

}
