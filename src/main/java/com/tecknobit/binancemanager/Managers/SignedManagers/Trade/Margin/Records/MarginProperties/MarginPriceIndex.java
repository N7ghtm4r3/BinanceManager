package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginProperties;

/**
 *  The {@code MarginPriceIndex} class is useful to format Binance Query Margin PriceIndex request
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-priceindex-market_data
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class MarginPriceIndex {

    private final long calcTime;
    private final double price;
    private final String symbol;

    public MarginPriceIndex(long calcTime, double price, String symbol) {
        this.calcTime = calcTime;
        this.price = price;
        this.symbol = symbol;
    }

    public long getCalcTime() {
        return calcTime;
    }

    public double getPrice() {
        return price;
    }

    public String getSymbol() {
        return symbol;
    }

}
