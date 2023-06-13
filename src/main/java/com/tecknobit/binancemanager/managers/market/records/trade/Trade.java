package com.tecknobit.binancemanager.managers.market.records.trade;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code Trade} class is useful to format {@code "Binance"} Recent Trade request
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list">
 *             Recent Trades List</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#account-trade-list-user_data">
 *             Account Trade List (USER_DATA)</a>
 *     </li>
 * </ul>
 */
public class Trade {

    /**
     * {@code id} is instance that contains trade id
     */
    protected final long id;

    /**
     * {@code price} is instance that contains price in trade
     */
    protected final double price;

    /**
     * {@code qty} is instance that contains quantity in trade
     */
    protected final double qty;

    /**
     * {@code quoteQty} is instance that contains quote quantity in trade
     */
    protected final double quoteQty;

    /**
     * {@code time} is instance that contains time of trade
     */
    protected final long time;

    /**
     * {@code isBuyerMaker} is instance that contains if trade is buyer maker
     */
    protected final boolean isBuyerMaker;

    /**
     * {@code isBestMatch} is instance that contains if is best match of trade
     */
    protected final boolean isBestMatch;

    /** Constructor to init {@link Trade} object
     * @param id: trade id
     * @param price: price in trade
     * @param qty: quantity in trade
     * @param quoteQty: quote quantity in trade
     * @param time: time of the trade
     * @param isBuyerMaker: trade is buyer maker
     * @param isBestMatch: is best match of trade
     */
    public Trade(long id, double price, double qty, double quoteQty, long time, boolean isBuyerMaker, boolean isBestMatch) {
        this.id = id;
        this.price = price;
        this.qty = qty;
        this.quoteQty = quoteQty;
        this.time = time;
        this.isBuyerMaker = isBuyerMaker;
        this.isBestMatch = isBestMatch;
    }

    /**
     * Constructor to init {@link Trade} object
     *
     * @param trade: trade details as {@link JSONObject}
     */
    public Trade(JSONObject trade) {
        id = trade.getLong("id");
        price = trade.getDouble("price");
        qty = trade.getDouble("qty");
        quoteQty = trade.getDouble("quoteQty");
        time = trade.getLong("time");
        isBuyerMaker = trade.getBoolean("isBuyerMaker");
        isBestMatch = trade.getBoolean("isBestMatch");
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
     * Method to get {@link #price} instance <br>
     * No-any params required
     *
     * @return {@link #price} instance as double
     */
    public double getPrice() {
        return price;
    }

    /**
     * Method to get {@link #price} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #price} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getPrice(int decimals) {
        return roundValue(price, decimals);
    }

    /**
     * Method to get {@link #qty} instance <br>
     * No-any params required
     *
     * @return {@link #qty} instance as double
     */
    public double getQty() {
        return qty;
    }

    /**
     * Method to get {@link #qty} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #qty} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getQty(int decimals) {
        return roundValue(qty, decimals);
    }

    /**
     * Method to get {@link #quoteQty} instance <br>
     * No-any params required
     *
     * @return {@link #quoteQty} instance as double
     */
    public double getQuoteQty() {
        return quoteQty;
    }

    /**
     * Method to get {@link #quoteQty} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #quoteQty} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getQuoteQty(int decimals) {
        return roundValue(quoteQty, decimals);
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
     * Method to get {@link #isBuyerMaker} instance <br>
     * No-any params required
     *
     * @return {@link #isBuyerMaker} instance as boolean
     */
    public boolean isBuyerMaker() {
        return isBuyerMaker;
    }

    /**
     * Method to get {@link #isBestMatch} instance <br>
     * No-any params required
     *
     * @return {@link #isBestMatch} instance as boolean
     */
    public boolean isBestMatch() {
        return isBestMatch;
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
