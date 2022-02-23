package com.tecknobit.binancemanager.Managers.Market.Records.Stats;

/**
 * The {@code Candlestick} class is useful to manage Candlestick requests
 * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data
 * @author N7ghtm4r3 - Tecknobit
 * **/


public class Candlestick {

    public static final String INTERVAL_1m = "1m";
    public static final String INTERVAL_3m = "3m";
    public static final String INTERVAL_5m = "5m";
    public static final String INTERVAL_15m = "15m";
    public static final String INTERVAL_30m = "30m";
    public static final String INTERVAL_1h = "1h";
    public static final String INTERVAL_2h = "2h";
    public static final String INTERVAL_4h = "4h";
    public static final String INTERVAL_6h = "6h";
    public static final String INTERVAL_8h = "8h";
    public static final String INTERVAL_12h = "12h";
    public static final String INTERVAL_1d = "1d";
    public static final String INTERVAL_3d = "3d";
    public static final String INTERVAL_1w = "1w";
    public static final String INTERVAL_1M = "1M";
    private final long openTime;
    private final double open;
    private final double high;
    private final double low;
    private final double close;
    private final double volume;
    private final long closeTime;
    private final double quoteAssetVolume;
    private final int numberOfTrades;
    private final double takerBuyBaseAssetVolume;
    private final double takerBuyQuoteAssetVolume;
    private final double valueToIgnore;

    public Candlestick(long openTime, double open, double high, double low, double close, double volume, long closeTime,
                       double quoteAssetVolume, int numberOfTrades, double takerBuyBaseAssetVolume,
                       double takerBuyQuoteAssetVolume, double valueToIgnore) {
        this.openTime = openTime;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.closeTime = closeTime;
        this.quoteAssetVolume = quoteAssetVolume;
        this.numberOfTrades = numberOfTrades;
        this.takerBuyBaseAssetVolume = takerBuyBaseAssetVolume;
        this.takerBuyQuoteAssetVolume = takerBuyQuoteAssetVolume;
        this.valueToIgnore = valueToIgnore;
    }

    public long getOpenTime() {
        return openTime;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }

    public double getVolume() {
        return volume;
    }

    public long getCloseTime() {
        return closeTime;
    }

    public double getQuoteAssetVolume() {
        return quoteAssetVolume;
    }

    public int getNumberOfTrades() {
        return numberOfTrades;
    }

    public double getTakerBuyBaseAssetVolume() {
        return takerBuyBaseAssetVolume;
    }

    public double getTakerBuyQuoteAssetVolume() {
        return takerBuyQuoteAssetVolume;
    }

    public double getValueToIgnore() {
        return valueToIgnore;
    }

}
