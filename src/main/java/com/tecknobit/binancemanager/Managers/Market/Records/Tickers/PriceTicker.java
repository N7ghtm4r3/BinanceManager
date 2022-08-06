package com.tecknobit.binancemanager.Managers.Market.Records.Tickers;

/**
 * The {@code PriceTicker} class is useful to manage PriceTicker requests
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker">
 *     https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class PriceTicker extends Ticker{

    /**
     * {@code price} is instance that contains price in the ticker
     * **/
    private final double price;

    /** Constructor to init {@link OrderBookTicker} object
     * @param symbol: symbol of the ticker
     * @param price: price in the ticker
     * **/
    public PriceTicker(String symbol, double price) {
        super(symbol);
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "PriceTicker{" +
                "price=" + price +
                ", symbol='" + symbol + '\'' +
                '}';
    }

}
