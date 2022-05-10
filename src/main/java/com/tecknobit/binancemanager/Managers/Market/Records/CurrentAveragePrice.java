package com.tecknobit.binancemanager.Managers.Market.Records;

/**
 * The {@code CurrentAveragePrice} class is useful to manage CurrentAveragePrice requests
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#current-average-price">https://binance-docs.github.io/apidocs/spot/en/#current-average-price</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class CurrentAveragePrice {

    private final int mins;
    private final double price;

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

}
