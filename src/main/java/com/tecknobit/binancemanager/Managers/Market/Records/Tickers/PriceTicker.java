package com.tecknobit.binancemanager.Managers.Market.Records.Tickers;

/**
 * The {@code PriceTicker} class is useful to manage PriceTicker requests
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker">https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class PriceTicker extends Ticker{

    private final double price;

    public PriceTicker(String symbol, double price) {
        super(symbol);
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

}
