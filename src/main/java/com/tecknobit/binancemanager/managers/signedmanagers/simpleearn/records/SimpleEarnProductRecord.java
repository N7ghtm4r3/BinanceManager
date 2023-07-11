package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.SimpleEarnProductStructure.SimpleEarnStatus;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code SimpleEarnProductRecord} class is useful to format a {@code Binance}'s simple earn product record
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see BinanceItem
 */
@Structure
public abstract class SimpleEarnProductRecord extends BinanceItem {

    /**
     * {@code amount} of the simple earn product record
     */
    protected final double amount;

    /**
     * {@code asset} of the simple earn product record
     */
    protected final String asset;

    /**
     * {@code time} of the simple earn product record
     */
    protected final long time;

    /**
     * {@code status} of the simple earn product record
     */
    protected final SimpleEarnStatus status;

    /**
     * Constructor to init a {@link SimpleEarnProductRecord} object
     *
     * @param amount: amount of the simple earn product record
     * @param asset:  asset of the simple earn product record
     * @param time:   time of the simple earn product record
     * @param status: status of the simple earn product record
     */
    public SimpleEarnProductRecord(double amount, String asset, long time, SimpleEarnStatus status) {
        super(null);
        this.amount = amount;
        this.asset = asset;
        this.time = time;
        this.status = status;
    }

    /**
     * Constructor to init a {@link SimpleEarnProductRecord} object
     *
     * @param jSimpleEarnProductRecord: simple earn product record details as {@link JSONObject}
     */
    public SimpleEarnProductRecord(JSONObject jSimpleEarnProductRecord) {
        super(jSimpleEarnProductRecord);
        amount = hItem.getDouble("amount", 0);
        asset = hItem.getString("asset");
        time = hItem.getLong("time", -1);
        status = SimpleEarnStatus.valueOf(hItem.getString("status"));
    }

    /**
     * Method to get {@link #amount} instance <br>
     * No-any params required
     *
     * @return {@link #amount} instance as double
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Method to get {@link #amount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #amount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getAmount(int decimals) {
        return roundValue(amount, decimals);
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
     * Method to get {@link #time} instance <br>
     * No-any params required
     *
     * @return {@link #time} instance as long
     */
    public long getTime() {
        return time;
    }

    /**
     * Method to get {@link #time} instance <br>
     * No-any params required
     *
     * @return {@link #time} instance as {@link Date}
     */
    public Date getDate() {
        return TimeFormatter.getDate(time);
    }

    /**
     * Method to get {@link #status} instance <br>
     * No-any params required
     *
     * @return {@link #status} instance as {@link SimpleEarnStatus}
     */
    public SimpleEarnStatus getStatus() {
        return status;
    }

}
