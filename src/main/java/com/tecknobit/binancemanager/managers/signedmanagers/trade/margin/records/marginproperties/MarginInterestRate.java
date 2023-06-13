package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.marginproperties;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code MarginInterestRate} class is useful to format a {@code "Binance"}'s margin interest rate
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-interest-rate-history-user_data">
 * Query Margin Interest Rate History (USER_DATA)</a>
 */
public class MarginInterestRate {

    /**
     * {@code asset} is instance that memorizes asset
     */
    private final String asset;

    /**
     * {@code dailyInterestRate} is instance that memorizes daily interest rate value
     */
    private final double dailyInterestRate;

    /**
     * {@code timestamp} is instance that memorizes timestamp value
     */
    private final long timestamp;

    /**
     * {@code vipLevel} is instance that memorizes vip level value
     */
    private final int vipLevel;

    /** Constructor to init {@link MarginInterestRate} object
     * @param asset: asset
     * @param dailyInterestRate: interest on the asset
     * @param timestamp: accurate time value
     * @param vipLevel: interest rate value
     * @throws IllegalArgumentException if parameters range is not respected
     */
    public MarginInterestRate(String asset, double dailyInterestRate, long timestamp, int vipLevel) {
        this.asset = asset;
        if(dailyInterestRate < 0)
            throw new IllegalArgumentException("Daily interest rate value cannot be less than 0");
        else
            this.dailyInterestRate = dailyInterestRate;
        if (timestamp < 0)
            throw new IllegalArgumentException("Timestamp value cannot be less than 0");
        else
            this.timestamp = timestamp;
        if (vipLevel < 0)
            throw new IllegalArgumentException("Vip level value cannot be less than 0");
        else
            this.vipLevel = vipLevel;
    }

    /**
     * Constructor to init {@link MarginInterestRate} object
     *
     * @param interestRate: interest rate details as {@link JSONObject}
     * @throws IllegalArgumentException if parameters range is not respected
     */
    public MarginInterestRate(JSONObject interestRate) {
        asset = interestRate.getString("asset");
        dailyInterestRate = interestRate.getDouble("dailyInterestRate");
        if (dailyInterestRate < 0)
            throw new IllegalArgumentException("Daily interest rate value cannot be less than 0");
        timestamp = interestRate.getLong("timestamp");
        if (timestamp < 0)
            throw new IllegalArgumentException("Timestamp value cannot be less than 0");
        vipLevel = interestRate.getInt("vipLevel");
        if (vipLevel < 0)
            throw new IllegalArgumentException("Vip level value cannot be less than 0");
    }

    /**
     * Method to get {@link #asset} instance <br>
     * No-any params required
     *
     * @return {@link #asset} instance as {@link String}
     */
    public String getAsset() {
        return asset;
    }

    /**
     * Method to get {@link #dailyInterestRate} instance <br>
     * No-any params required
     *
     * @return {@link #dailyInterestRate} instance as double
     */
    public double getDailyInterestRate() {
        return dailyInterestRate;
    }

    /**
     * Method to get {@link #dailyInterestRate} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #dailyInterestRate} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getDailyInterestRate(int decimals) {
        return roundValue(dailyInterestRate, decimals);
    }

    /**
     * Method to get {@link #timestamp} instance <br>
     * No-any params required
     *
     * @return {@link #timestamp} instance as long
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Method to get {@link #timestamp} instance <br>
     * No-any params required
     *
     * @return {@link #timestamp} instance as {@link Date}
     */
    public Date getDate() {
        return TimeFormatter.getDate(timestamp);
    }

    /**
     * Method to get {@link #vipLevel} instance <br>
     * No-any params required
     *
     * @return {@link #vipLevel} instance as int
     */
    public int getVipLevel() {
        return vipLevel;
    }

    /**
     * Returns a string representation of the object <br>
     * No-any params required
     *
     * @return a string representation of the object as {@link String}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

}
