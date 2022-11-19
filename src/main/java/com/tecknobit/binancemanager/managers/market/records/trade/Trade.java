package com.tecknobit.binancemanager.managers.market.records.trade;

import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code Trade} class is useful to format Binance Recent Trade request
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list">
 * https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list</a>
 **/

public class Trade {

    /**
     * {@code id} is instance that contains trade id
     * **/
    private final long id;

    /**
     * {@code price} is instance that contains price in trade
     * **/
    private final double price;

    /**
     * {@code qty} is instance that contains quantity in trade
     * **/
    private final double qty;

    /**
     * {@code quoteQty} is instance that contains quote quantity in trade
     * **/
    private final double quoteQty;

    /**
     * {@code time} is instance that contains time of trade
     * **/
    private final long time;

    /**
     * {@code isBuyerMaker} is instance that contains if trade is buyer maker
     * **/
    private final boolean isBuyerMaker;

    /**
     * {@code isBestMatch} is instance that contains if is best match of trade
     * **/
    private final boolean isBestMatch;

    /** Constructor to init {@link Trade} object
     * @param id: trade id
     * @param price: price in trade
     * @param qty: quantity in trade
     * @param quoteQty: quote quantity in trade
     * @param time: time of the trade
     * @param isBuyerMaker: trade is buyer maker
     * @param isBestMatch: is best match of trade
     * **/
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
     **/
    public Trade(JSONObject trade) {
        id = trade.getLong("id");
        price = trade.getDouble("price");
        qty = trade.getDouble("qty");
        quoteQty = trade.getDouble("quoteQty");
        time = trade.getLong("time");
        isBuyerMaker = trade.getBoolean("isBuyerMaker");
        isBestMatch = trade.getBoolean("isBestMatch");
    }

    public long getId() {
        return id;
    }

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

    public double getQty() {
        return qty;
    }

    /**
     * Method to get {@link #qty} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #qty} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getQty(int decimals) {
        return roundValue(qty, decimals);
    }

    public double getQuoteQty() {
        return quoteQty;
    }

    /**
     * Method to get {@link #quoteQty} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #quoteQty} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getQuoteQty(int decimals) {
        return roundValue(quoteQty, decimals);
    }

    public long getTime() {
        return time;
    }

    public boolean isBuyerMaker() {
        return isBuyerMaker;
    }

    public boolean isBestMatch() {
        return isBestMatch;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "id=" + id +
                ", price=" + price +
                ", qty=" + qty +
                ", quoteQty=" + quoteQty +
                ", time=" + time +
                ", isBuyerMaker=" + isBuyerMaker +
                ", isBestMatch=" + isBestMatch +
                '}';
    }

}
