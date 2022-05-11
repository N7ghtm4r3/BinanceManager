package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginProperties;

/**
 *  The {@code MarginPriceIndex} class is useful to format Binance Query Margin PriceIndex request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-priceindex-market_data">https://binance-docs.github.io/apidocs/spot/en/#query-margin-priceindex-market_data</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class MarginPriceIndex {

    private long calcTime;
    private double price;
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

    public void setPrice(double price) {
        if(price < 0)
            throw new IllegalArgumentException("Price value cannot be less than 0");
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setCalcTime(long calcTime) {
        if(calcTime < 0)
            throw new IllegalArgumentException("Calc time value cannot be less than 0");
        this.calcTime = calcTime;
    }

}
