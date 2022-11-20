package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.marginproperties;

import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code MarginPriceIndex} class is useful to format {@code "Binance"} Query Margin PriceIndex request
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-priceindex-market_data">
 * https://binance-docs.github.io/apidocs/spot/en/#query-margin-priceindex-market_data</a>
 **/

public class MarginPriceIndex {

    /**
     * {@code calcTime} is instance that memorizes calc time
     * **/
    private long calcTime;

    /**
     * {@code price} is instance that memorizes price
     * **/
    private double price;

    /**
     * {@code symbol} is instance that memorizes symbol
     * **/
    private final String symbol;

    /** Constructor to init {@link MarginPriceIndex} object
     * @param calcTime: calc time
     * @param price: price
     * @param symbol: symbol
     * @throws IllegalArgumentException if parameters range is not respected
     * **/
    public MarginPriceIndex(long calcTime, double price, String symbol) {
        if(calcTime < 0)
            throw new IllegalArgumentException("Calc time value cannot be less than 0");
        else
            this.calcTime = calcTime;
        if (price < 0)
            throw new IllegalArgumentException("Price value cannot be less than 0");
        else
            this.price = price;
        this.symbol = symbol;
    }

    /**
     * Constructor to init {@link MarginPriceIndex} object
     *
     * @param priceIndex: price index details as {@link JSONObject}
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public MarginPriceIndex(JSONObject priceIndex) {
        calcTime = priceIndex.getLong("calcTime");
        if (calcTime < 0)
            throw new IllegalArgumentException("Calc time value cannot be less than 0");
        price = priceIndex.getDouble("price");
        if (price < 0)
            throw new IllegalArgumentException("Price value cannot be less than 0");
        symbol = priceIndex.getString("symbol");
    }

    public long getCalcTime() {
        return calcTime;
    }

    /**
     * Method to set {@link #calcTime}
     *
     * @param calcTime: calc time value
     * @throws IllegalArgumentException when calc time value is less than 0
     **/
    public void setCalcTime(long calcTime) {
        if (calcTime < 0)
            throw new IllegalArgumentException("Calc time value cannot be less than 0");
        this.calcTime = calcTime;
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

    /**
     * Method to set {@link #price}
     *
     * @param price: price value
     * @throws IllegalArgumentException when price value is less than 0
     **/
    public void setPrice(double price) {
        if (price < 0)
            throw new IllegalArgumentException("Price value cannot be less than 0");
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return "MarginPriceIndex{" +
                "calcTime=" + calcTime +
                ", price=" + price +
                ", symbol='" + symbol + '\'' +
                '}';
    }

}
