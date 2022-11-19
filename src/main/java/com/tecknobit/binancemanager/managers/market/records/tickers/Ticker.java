package com.tecknobit.binancemanager.managers.market.records.tickers;

/**
 * The {@code Ticker} class is main class to manage all Binance Tickers requests
 * @author N7ghtm4r3 - Tecknobit
 * **/

public abstract class Ticker {

    /**
     * {@code FULL_TYPE_RESPONSE} is constant for full type response
     * **/
    public static final String FULL_TYPE_RESPONSE = "FULL";

    /**
     * {@code MINI_TYPE_RESPONSE} is constant for mini type response
     * **/
    public static final String MINI_TYPE_RESPONSE = "MINI";

    /**
     * {@code symbol} is instance that contains symbol of the ticker
     * **/
    protected final String symbol;

    /** Constructor to init {@link Ticker} object
     * @param symbol: symbol of the ticker
     * **/
    public Ticker(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return "Ticker{" +
                "symbol='" + symbol + '\'' +
                '}';
    }

}
