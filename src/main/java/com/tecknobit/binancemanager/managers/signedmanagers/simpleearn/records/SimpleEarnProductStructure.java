package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.Date;

/**
 * The {@code SimpleEarnProductStructure} class is useful to format a {@code Binance}'s simple earn product structure
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see BinanceItem
 */
@Structure
public abstract class SimpleEarnProductStructure extends BinanceItem {

    /**
     * {@code SimpleEarnStatus} list of available earn statuses
     */
    public enum SimpleEarnStatus {

        /**
         * {@code PURCHASING} earn status
         */
        PURCHASING,

        /**
         * {@code CREATED} earn status
         */
        CREATED,

        /**
         * {@code PAID} earn status
         */
        PAID,

        /**
         * {@code SUCCESS} earn status
         */
        SUCCESS

    }

    /**
     * {@code asset} of the simple earn product
     */
    protected final String asset;

    /**
     * {@code isSoldOut} whether the simple earn product is sold out
     */
    protected final boolean isSoldOut;

    /**
     * {@code status} of the simple earn product
     */
    protected final SimpleEarnStatus status;

    /**
     * {@code subscriptionStartTime} when the simple earn product has been subscribed
     */
    protected final long subscriptionStartTime;

    /**
     * Constructor to init a {@link SimpleEarnProductStructure} object
     *
     * @param asset:                 asset of the simple earn product
     * @param isSoldOut:             whether the simple earn product is sold out
     * @param status:                status of the simple earn product
     * @param subscriptionStartTime: when the simple earn product has been subscribed
     */
    public SimpleEarnProductStructure(String asset, boolean isSoldOut, SimpleEarnStatus status, long subscriptionStartTime) {
        super(null);
        this.asset = asset;
        this.isSoldOut = isSoldOut;
        this.status = status;
        this.subscriptionStartTime = subscriptionStartTime;
    }

    /**
     * Constructor to init a {@link SimpleEarnProductStructure} object
     *
     * @param jSimpleEarnProductStructure: simple earn product structure details as {@link JSONObject}
     */
    public SimpleEarnProductStructure(JSONObject jSimpleEarnProductStructure) {
        super(jSimpleEarnProductStructure);
        asset = hItem.getString("asset");
        isSoldOut = hItem.getBoolean("isSoldOut");
        subscriptionStartTime = hItem.getLong("subscriptionStartTime", -1);
        status = SimpleEarnStatus.valueOf(hItem.getString("status"));
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
     * Method to get {@link #isSoldOut} instance <br>
     * No-any params required
     *
     * @return {@link #isSoldOut} instance as boolean
     */
    public boolean isSoldOut() {
        return isSoldOut;
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

    /**
     * Method to get {@link #subscriptionStartTime} instance <br>
     * No-any params required
     *
     * @return {@link #subscriptionStartTime} instance as long
     */
    public long getSubscriptionStartTime() {
        return subscriptionStartTime;
    }

    /**
     * Method to get {@link #subscriptionStartTime} instance <br>
     * No-any params required
     *
     * @return {@link #subscriptionStartTime} instance as {@link Date}
     */
    public Date getSubscriptionStartDate() {
        return TimeFormatter.getDate(subscriptionStartTime);
    }

}
