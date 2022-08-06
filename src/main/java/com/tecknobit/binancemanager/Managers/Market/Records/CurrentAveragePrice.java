package com.tecknobit.binancemanager.Managers.Market.Records;

/**
 * The {@code CurrentAveragePrice} class is useful to manage CurrentAveragePrice requests
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#current-average-price">
 * https://binance-docs.github.io/apidocs/spot/en/#current-average-price</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class CurrentAveragePrice {

    /**
     * {@code mins} is instance that contains mins details
     * **/
    private final int mins;

    /**
     * {@code price} is instance that contains price details
     * **/
    private final double price;

    /** Constructor to init {@link OrderBook} object
     * @param mins: mins details
     * @param price: price details
     * **/
    public CurrentAveragePrice(int mins, double price) {
        this.mins = mins;
        this.price = price;
    }

    public int getMins() {
        return mins;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "CurrentAveragePrice{" +
                "mins=" + mins +
                ", price=" + price +
                '}';
    }

}
