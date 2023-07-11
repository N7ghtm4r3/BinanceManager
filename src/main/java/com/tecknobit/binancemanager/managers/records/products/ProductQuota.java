package com.tecknobit.binancemanager.managers.records.products;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code ProductQuota} class is useful to format a product quota
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see BinanceItem
 */
public class ProductQuota extends BinanceItem {

    /**
     * {@code totalPersonalQuota} total Personal quota
     */
    private final double totalPersonalQuota;

    /**
     * {@code minimum} minimum amount per order
     */
    private final double minimum;

    /**
     * Constructor to init {@link ProductQuota} object
     *
     * @param totalPersonalQuota: total Personal quota
     * @param minimum:            minimum amount per order
     */
    public ProductQuota(double totalPersonalQuota, double minimum) {
        super(null);
        this.totalPersonalQuota = totalPersonalQuota;
        this.minimum = minimum;
    }

    /**
     * Constructor to init {@link ProductQuota} object
     *
     * @param jStakingQuota: staking quota details as {@link JSONObject}
     */
    public ProductQuota(JSONObject jStakingQuota) {
        super(jStakingQuota);
        totalPersonalQuota = hItem.getDouble("totalPersonalQuota", 0);
        minimum = hItem.getDouble("minimum", 0);
    }

    /**
     * Method to get {@link #totalPersonalQuota} instance <br>
     * No-any params required
     *
     * @return {@link #totalPersonalQuota} instance as double
     */
    public double getTotalPersonalQuota() {
        return totalPersonalQuota;
    }

    /**
     * Method to get {@link #totalPersonalQuota} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalPersonalQuota} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getTotalPersonalQuota(int decimals) {
        return roundValue(totalPersonalQuota, decimals);
    }

    /**
     * Method to get {@link #minimum} instance <br>
     * No-any params required
     *
     * @return {@link #minimum} instance as double
     */
    public double getMinimum() {
        return minimum;
    }

    /**
     * Method to get {@link #minimum} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #minimum} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getMinimum(int decimals) {
        return roundValue(minimum, decimals);
    }

}
