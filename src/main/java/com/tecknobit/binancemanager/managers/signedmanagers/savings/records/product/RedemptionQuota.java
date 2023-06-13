package com.tecknobit.binancemanager.managers.signedmanagers.savings.records.product;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code RedemptionQuota} class is useful to format a redemption quota
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-left-daily-redemption-quota-of-flexible-product-user_data">
 * Get Left Daily Redemption Quota of Flexible Product (USER_DATA)</a>
 * @see BinanceItem
 * @see PurchaseQuota
 */
public class RedemptionQuota extends PurchaseQuota {

    /**
     * {@code dailyQuota} daily quota of the redemption
     */
    private final double dailyQuota;

    /**
     * {@code minRedemptionAmount} min redemption amount of the redemption quota
     */
    private final double minRedemptionAmount;

    /**
     * Constructor to init {@link RedemptionQuota} object
     *
     * @param asset:               asset of the redemption quota
     * @param leftQuota:           left quota of the redemption quota
     * @param dailyQuota:          daily quota of the redemption
     * @param minRedemptionAmount: min redemption amount of the redemption quota
     */
    public RedemptionQuota(String asset, double leftQuota, double dailyQuota, double minRedemptionAmount) {
        super(asset, leftQuota);
        this.dailyQuota = dailyQuota;
        this.minRedemptionAmount = minRedemptionAmount;
    }

    /**
     * Constructor to init {@link RedemptionQuota} object
     *
     * @param jRedemptionQuota: redemption quota details as {@link JSONObject}
     */
    public RedemptionQuota(JSONObject jRedemptionQuota) {
        super(jRedemptionQuota);
        dailyQuota = hItem.getDouble("dailyQuota", 0);
        minRedemptionAmount = hItem.getDouble("minRedemptionAmount", 0);
    }

    /**
     * Method to get {@link #dailyQuota} instance <br>
     * No-any params required
     *
     * @return {@link #dailyQuota} instance as double
     */
    public double getDailyQuota() {
        return dailyQuota;
    }

    /**
     * Method to get {@link #dailyQuota} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #dailyQuota} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getDailyQuota(int decimals) {
        return roundValue(dailyQuota, decimals);
    }

    /**
     * Method to get {@link #minRedemptionAmount} instance <br>
     * No-any params required
     *
     * @return {@link #minRedemptionAmount} instance as double
     */
    public double getMinRedemptionAmount() {
        return minRedemptionAmount;
    }

    /**
     * Method to get {@link #minRedemptionAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #minRedemptionAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getMinRedemptionAmount(int decimals) {
        return roundValue(minRedemptionAmount, decimals);
    }

}
