package com.tecknobit.binancemanager.Managers.Market.Records.Tickers;

/**
 * The {@code Ticker} class is main class to manage all Binance Tickers requests
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class Ticker {

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

}
