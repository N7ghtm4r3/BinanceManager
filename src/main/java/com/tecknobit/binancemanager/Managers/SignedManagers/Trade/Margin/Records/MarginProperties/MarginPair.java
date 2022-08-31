package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginProperties;

import org.json.JSONObject;

/**
 * The {@code MarginPair} class is useful to format Binance Get All Cross Margin Pairs request
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-all-cross-margin-pairs-market_data">
 * https://binance-docs.github.io/apidocs/spot/en/#get-all-cross-margin-pairs-market_data</a>
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
        id = marginPair.getLong("id");
        symbol = marginPair.getString("symbol");
        base = marginPair.getString("base");
        quote = marginPair.getString("quote");
        isMarginTrade = marginPair.getBoolean("isMarginTrade");
        isBuyAllowed = marginPair.getBoolean("isBuyAllowed");
        isSellAllowed = marginPair.getBoolean("isSellAllowed");
    }

    public long getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getBase() {
        return base;
    }

    public String getQuote() {
        return quote;
    }

    public boolean isMarginTrade() {
        return isMarginTrade;
    }

    public boolean isBuyAllowed() {
        return isBuyAllowed;
    }

    public boolean isSellAllowed() {
        return isSellAllowed;
    }

    @Override
    public String toString() {
        return "MarginPair{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                ", base='" + base + '\'' +
                ", quote='" + quote + '\'' +
                ", isMarginTrade=" + isMarginTrade +
                ", isBuyAllowed=" + isBuyAllowed +
                ", isSellAllowed=" + isSellAllowed +
                '}';
    }

}
