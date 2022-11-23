package com.tecknobit.binancemanager.managers.market.records.tickers;

import org.json.JSONObject;

/**
 * The {@code Ticker} class is main class to format a ticker object
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker">
 *             Symbol Order Book Ticker</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker">
 *             Symbol Price Ticker</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
 *             Rolling window price change statistics</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
 *             24hr Ticker Price Change Statistics</a>
 *     </li>
 * </ul>
 **/
public abstract class Ticker {

    /**
     * {@code symbol} is instance that contains symbol of the ticker
     * **/
    protected final String symbol;

    /**
     * Constructor to init {@link Ticker} object
     *
     * @param symbol: symbol of the ticker
     **/
    public Ticker(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Method to get {@link #symbol} instance <br>
     * Any params required
     *
     * @return {@link #symbol} instance as {@link String}
     **/
    public String getSymbol() {
        return symbol;
    }

    /**
     * Returns a string representation of the object <br>
     * Any params required
     *
     * @return a string representation of the object as {@link String}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

    /**
     * {@code ResponseType} list of available responses type
     **/
    public enum ResponseType {

        /**
         * {@code "FULL"} response type
         **/
        FULL,

        /**
         * {@code "MINI"} response type
         **/
        MINI

    }

}
