package com.tecknobit.binancemanager.managers.market.records.tickers;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code PriceTicker} class is useful to manage PriceTicker requests
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker">
 * Symbol Price Ticker</a>
 * @see Ticker
 **/
public class PriceTicker extends Ticker {

    /**
     * {@code price} is instance that contains price in the ticker
     **/
    private final double price;

    /**
     * Constructor to init {@link PriceTicker} object
     *
     * @param symbol: symbol of the ticker
     * @param price:  price in the ticker
     **/
    public PriceTicker(String symbol, double price) {
        super(symbol);
        this.price = price;
    }

    /**
     * Method to get {@link #price} instance <br>
     * Any params required
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

}
