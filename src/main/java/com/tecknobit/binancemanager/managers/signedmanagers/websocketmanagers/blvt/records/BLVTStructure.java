package com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.blvt.records;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code BLVTStructure} class is useful to format a BLVT structure
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#subscribe-blvt-user_data">
 *             Subscribe BLVT (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#query-subscription-record-user_data">
 *             Query Subscription Record (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-blvt-user_data">
 *             Redeem BLVT (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#query-redemption-record-user_data">
 *             Query Redemption Record (USER_DATA)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 */
@Structure
public abstract class BLVTStructure extends BinanceItem {

    /**
     * {@code BLVTStatus} list of available BLVT statuses
     */
    public enum BLVTStatus {

        /**
         * {@code S} BLVT status
         */
        S,

        /**
         * {@code P} BLVT status
         */
        P,

        /**
         * {@code F} BLVT status
         */
        F

    }

    /**
     * {@code id} of the BLVT
     */
    protected final long id;

    /**
     * {@code tokenName} token name of the BLVT
     */
    protected final String tokenName;

    /**
     * {@code amount} of the BLVT
     */
    protected final double amount;

    /**
     * {@code timestamp} of the BLVT
     */
    protected final long timestamp;

    /**
     * Constructor to init {@link BLVTStructure} object
     *
     * @param id:        id of the BLVT
     * @param tokenName: token name of the BLVT
     * @param amount:    amount of the BLVT
     * @param timestamp: timestamp of the BLVT
     */
    public BLVTStructure(long id, String tokenName, double amount, long timestamp) {
        super(null);
        this.id = id;
        this.tokenName = tokenName;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    /**
     * Constructor to init {@link BLVTStructure} object
     *
     * @param jBLVTStructure: BLVT structure details as {@link JSONObject}
     */
    public BLVTStructure(JSONObject jBLVTStructure) {
        super(jBLVTStructure);
        id = hItem.getLong("id", 0);
        tokenName = hItem.getString("tokenName");
        amount = hItem.getDouble("amount", 0);
        timestamp = hItem.getLong("timestamp", 0);
    }

    /**
     * Method to get {@link #id} instance <br>
     * No-any params required
     *
     * @return {@link #id} instance as long
     */
    public long getId() {
        return id;
    }

    /**
     * Method to get {@link #tokenName} instance <br>
     * No-any params required
     *
     * @return {@link #tokenName} instance as {@link String}
     */
    public String getTokenName() {
        return tokenName;
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

}
