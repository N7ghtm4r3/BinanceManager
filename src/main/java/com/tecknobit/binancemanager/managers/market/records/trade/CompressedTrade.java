package com.tecknobit.binancemanager.managers.market.records.trade;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code CompressedTrade} class is useful to format compressed trade in market endpoints type
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#compressed-aggregate-trades-list">
 * Compressed/Aggregate Trades List</a>
 **/
public class CompressedTrade {

    /**
     * {@code aggregateTradeId} is instance that contains aggregate trade id
     * **/
    private final long aggregateTradeId;

    /**
     * {@code price} is instance that contains price in compressed trade
     * **/
    private final double price;

    /**
     * {@code quantity} is instance that contains quantity in compressed trade
     * **/
    private final double quantity;

    /**
     * {@code firstTradeId} is instance that contains first trade id of compressed trade
     * **/
    private final long firstTradeId;

    /**
     * {@code lastTradeId} is instance that contains last trade id of compressed trade
     * **/
    private final long lastTradeId;

    /**
     * {@code timestamp} is instance that contains timestamp of compressed trade
     * **/
    private final long timestamp;

    /**
     * {@code isBuyerMaker} is instance that contains if compressed trade is buyer maker
     * **/
    private final boolean isBuyerMaker;

    /**
     * {@code isBestMatch} is instance that contains if is best match of compressed trade
     * **/
    private final boolean isBestMatch;

    /** Constructor to init {@link CompressedTrade} object
     * @param aggregateTradeId: aggregate trade id
     * @param price: price in compressed trade
     * @param quantity: quantity in compressed trade
     * @param firstTradeId: first trade id of compressed trade
     * @param lastTradeId: last trade id of compressed trade
     * @param timestamp: timestamp of compressed trade
     * @param isBuyerMaker: compressed trade is buyer maker
     * @param isBestMatch: is best match of compressed trade
     * **/
    public CompressedTrade(long aggregateTradeId, double price, double quantity, long firstTradeId, long lastTradeId,
                           long timestamp, boolean isBuyerMaker, boolean isBestMatch) {
        this.aggregateTradeId = aggregateTradeId;
        this.price = price;
        this.quantity = quantity;
        this.firstTradeId = firstTradeId;
        this.lastTradeId = lastTradeId;
        this.timestamp = timestamp;
        this.isBuyerMaker = isBuyerMaker;
        this.isBestMatch = isBestMatch;
    }

    /**
     * Constructor to init {@link CompressedTrade} object
     *
     * @param compressedTrade: compressed trade details as {@link JSONObject}
     **/
    public CompressedTrade(JSONObject compressedTrade) {
        aggregateTradeId = compressedTrade.getLong("a");
        price = compressedTrade.getDouble("p");
        quantity = compressedTrade.getDouble("q");
        firstTradeId = compressedTrade.getLong("f");
        lastTradeId = compressedTrade.getLong("l");
        timestamp = compressedTrade.getLong("T");
        isBuyerMaker = compressedTrade.getBoolean("m");
        isBestMatch = compressedTrade.getBoolean("M");
    }

    /**
     * Method to get {@link #aggregateTradeId} instance <br>
     * No-any params required
     *
     * @return {@link #aggregateTradeId} instance as long
     **/
    public long getAggregateTradeId() {
        return aggregateTradeId;
    }

    /**
     * Method to get {@link #price} instance <br>
     * No-any params required
     *
     * @return {@link #price} instance as double
     **/
    public double getPrice() {
        return price;
    }

    /**
     * Method to get {@link #price} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #price} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getPrice(int decimals) {
        return roundValue(price, decimals);
    }

    /**
     * Method to get {@link #quantity} instance <br>
     * No-any params required
     *
     * @return {@link #quantity} instance as double
     **/
    public double getQuantity() {
        return quantity;
    }

    /**
     * Method to get {@link #quantity} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #quantity} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getQuantity(int decimals) {
        return roundValue(quantity, decimals);
    }

    /**
     * Method to get {@link #firstTradeId} instance <br>
     * No-any params required
     *
     * @return {@link #firstTradeId} instance as long
     **/
    public long getFirstTradeId() {
        return firstTradeId;
    }

    /**
     * Method to get {@link #lastTradeId} instance <br>
     * No-any params required
     *
     * @return {@link #lastTradeId} instance as long
     **/
    public long getLastTradeId() {
        return lastTradeId;
    }

    /**
     * Method to get {@link #timestamp} instance <br>
     * No-any params required
     *
     * @return {@link #timestamp} instance as long
     **/
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Method to get {@link #timestamp} instance <br>
     * No-any params required
     *
     * @return {@link #timestamp} instance as {@link Date}
     **/
    public Date getDate() {
        return TimeFormatter.getDate(timestamp);
    }

    /**
     * Method to get {@link #isBuyerMaker} instance <br>
     * No-any params required
     *
     * @return {@link #isBuyerMaker} instance as boolean
     **/
    public boolean isBuyerMaker() {
        return isBuyerMaker;
    }

    /**
     * Method to get {@link #isBestMatch} instance <br>
     * No-any params required
     *
     * @return {@link #isBestMatch} instance as boolean
     **/
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
