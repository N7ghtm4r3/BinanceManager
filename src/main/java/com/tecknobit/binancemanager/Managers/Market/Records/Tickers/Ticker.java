package com.tecknobit.binancemanager.Managers.Market.Records.Tickers;

/**
 * The {@code Ticker} class is main class to manage all Binance Tickers requests
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class Ticker {

    private final String symbol;

    public Ticker(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

}
