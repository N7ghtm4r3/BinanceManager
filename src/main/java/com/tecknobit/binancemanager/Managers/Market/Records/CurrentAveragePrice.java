package com.tecknobit.binancemanager.Managers.Market.Records;

import org.json.JSONObject;

import static com.tecknobit.apimanager.Tools.Trading.TradingTools.roundValue;

/**
 * The {@code CurrentAveragePrice} class is useful to manage CurrentAveragePrice requests
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#current-average-price">
 * https://binance-docs.github.io/apidocs/spot/en/#current-average-price</a>
 **/

public class CurrentAveragePrice {

    /**
     * {@code mins} is instance that contains mins details
     * **/
    private final int mins;

    /**
     * {@code price} is instance that contains price details
     * **/
    private final double price;

    /**
     * Constructor to init {@link OrderBook} object
     *
     * @param mins:  mins details
     * @param price: price details
     **/
    public CurrentAveragePrice(int mins, double price) {
        this.mins = mins;
        this.price = price;
    }

    /**
     * Constructor to init {@link CurrentAveragePrice} object
     *
     * @param avgPrice: average price details as {@link JSONObject}
     **/
    public CurrentAveragePrice(JSONObject avgPrice) {
        mins = avgPrice.getInt("mins");
        price = avgPrice.getDouble("price");
    }

    public int getMins() {
        return mins;
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

    @Override
    public String toString() {
        return "CurrentAveragePrice{" +
                "mins=" + mins +
                ", price=" + price +
                '}';
    }

}
