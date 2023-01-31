package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.marginproperties;

import org.json.JSONObject;

/**
 * The {@code MarginPair} class is useful to format a {@code "Binance"}'s margin pair
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-all-cross-margin-pairs-market_data">
 * Get All Cross Margin Pairs (MARKET_DATA)</a>
 **/
public class MarginPair {

    /**
     * {@code id} is instance that memorizes margin pair identifier
     * **/
    private final long id;

    /**
     * {@code symbol} is instance that memorizes margin pair symbol
     * **/
    private final String symbol;

    /**
     * {@code base} is instance that memorizes margin pair base asset
     * **/
    private final String base;

    /**
     * {@code quote} is instance that memorizes margin pair quote asset
     * **/
    private final String quote;

    /**
     * {@code isMarginTrade} is instance that memorizes if is margin trade
     * **/
    private final boolean isMarginTrade;

    /**
     * {@code isBuyAllowed} is instance that memorizes if is allowed to buy
     * **/
    private final boolean isBuyAllowed;

    /**
     * {@code isSellAllowed} is instance that memorizes if is allowed to sell
     * **/
    private final boolean isSellAllowed;

    /** Constructor to init {@link MarginPair} object
     * @param id: margin pair identifier
     * @param symbol: margin pair symbol
     * @param base: margin pair base asset
     * @param quote: margin pair quote asset
     * @param isMarginTrade: is margin trade
     * @param isBuyAllowed: is allowed to buy
     * @param isSellAllowed: is allowed to sell
     * **/
    public MarginPair(long id, String symbol, String base, String quote, boolean isMarginTrade, boolean isBuyAllowed,
                      boolean isSellAllowed) {
        this.id = id;
        this.symbol = symbol;
        this.base = base;
        this.quote = quote;
        this.isMarginTrade = isMarginTrade;
        this.isBuyAllowed = isBuyAllowed;
        this.isSellAllowed = isSellAllowed;
    }

    /**
     * Constructor to init {@link MarginPair} object
     *
     * @param marginPair: margin pair details as {@link JSONObject}
     **/
    public MarginPair(JSONObject marginPair) {
        this(marginPair.getLong("id"), marginPair.getString("symbol"), marginPair.getString("base"),
                marginPair.getString("quote"), marginPair.getBoolean("isMarginTrade"),
                marginPair.getBoolean("isBuyAllowed"), marginPair.getBoolean("isSellAllowed"));
    }

    /**
     * Method to get {@link #id} instance <br>
     * No-any params required
     *
     * @return {@link #id} instance as long
     **/
    public long getId() {
        return id;
    }

    /**
     * Method to get {@link #symbol} instance <br>
     * No-any params required
     *
     * @return {@link #symbol} instance as {@link String}
     **/
    public String getSymbol() {
        return symbol;
    }

    /**
     * Method to get {@link #base} instance <br>
     * No-any params required
     *
     * @return {@link #base} instance as {@link String}
     **/
    public String getBase() {
        return base;
    }

    /**
     * Method to get {@link #quote} instance <br>
     * No-any params required
     *
     * @return {@link #quote} instance as {@link String}
     **/
    public String getQuote() {
        return quote;
    }

    /**
     * Method to get {@link #isMarginTrade} instance <br>
     * No-any params required
     *
     * @return {@link #isMarginTrade} instance as boolean
     **/
    public boolean isMarginTrade() {
        return isMarginTrade;
    }

    /**
     * Method to get {@link #isBuyAllowed} instance <br>
     * No-any params required
     *
     * @return {@link #isBuyAllowed} instance as boolean
     **/
    public boolean isBuyAllowed() {
        return isBuyAllowed;
    }

    /**
     * Method to get {@link #isSellAllowed} instance <br>
     * No-any params required
     *
     * @return {@link #isSellAllowed} instance as boolean
     **/
    public boolean isSellAllowed() {
        return isSellAllowed;
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
