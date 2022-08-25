package com.tecknobit.binancemanager.Managers.Market.Records.Stats;

/**
 * The {@code Candlestick} class is useful to manage Candlestick requests
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data">
 *            https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data</a>
 *     </li>
 *      <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#uiklines">
 *           https://binance-docs.github.io/apidocs/spot/en/#uiklines</a>
 *     </li>
 * </ul>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class Candlestick {

    /**
     * {@code INTERVAL_1s} is constant for one second interval
     * **/
    public static final String INTERVAL_1s = "1s";

    /**
     * {@code INTERVAL_1m} is constant for one minute interval
     * **/
    public static final String INTERVAL_1m = "1m";

    /**
     * {@code INTERVAL_3m} is constant for three minutes interval
     * **/
    public static final String INTERVAL_3m = "3m";

    /**
     * {@code INTERVAL_5m} is constant for five minutes interval
     * **/
    public static final String INTERVAL_5m = "5m";

    /**
     * {@code INTERVAL_15m} is constant for fifteen minutes interval
     * **/
    public static final String INTERVAL_15m = "15m";

    /**
     * {@code INTERVAL_30m} is constant for thirty minutes interval
     * **/
    public static final String INTERVAL_30m = "30m";

    /**
     * {@code INTERVAL_1h} is constant for one hour interval
     * **/
    public static final String INTERVAL_1h = "1h";

    /**
     * {@code INTERVAL_2h} is constant for two hours interval
     * **/
    public static final String INTERVAL_2h = "2h";

    /**
     * {@code INTERVAL_4h} is constant for four hours interval
     * **/
    public static final String INTERVAL_4h = "4h";

    /**
     * {@code INTERVAL_4h} is constant for six hours interval
     * **/
    public static final String INTERVAL_6h = "6h";

    /**
     * {@code INTERVAL_8h} is constant for eight hours interval
     * **/
    public static final String INTERVAL_8h = "8h";

    /**
     * {@code INTERVAL_12h} is constant for twelve hours interval
     * **/
    public static final String INTERVAL_12h = "12h";

    /**
     * {@code INTERVAL_1d} is constant for one day interval
     * **/
    public static final String INTERVAL_1d = "1d";

    /**
     * {@code INTERVAL_3d} is constant for three days interval
     * **/
    public static final String INTERVAL_3d = "3d";

    /**
     * {@code INTERVAL_1w} is constant for one-week interval
     * **/
    public static final String INTERVAL_1w = "1w";

    /**
     * {@code INTERVAL_1M} is constant for one-month interval
     * **/
    public static final String INTERVAL_1M = "1M";

    /**
     * {@code openTime} is instance that contains open time of the candlestick
     * **/
    private final long openTime;

    /**
     * {@code open} is instance that contains open price of the candlestick
     * **/
    private final double open;

    /**
     * {@code high} is instance that contains high price of the candlestick
     * **/
    private final double high;

    /**
     * {@code low} is instance that contains low price of the candlestick
     * **/
    private final double low;

    /**
     * {@code close} is instance that contains close price of the candlestick
     * **/
    private final double close;

    /**
     * {@code volume} is instance that contains volume of the candlestick
     * **/
    private final double volume;

    /**
     * {@code closeTime} is instance that contains close time of the candlestick
     * **/
    private final long closeTime;

    /**
     * {@code quoteAssetVolume} is instance that contains quote asset volume of the candlestick
     * **/
    private final double quoteAssetVolume;

    /**
     * {@code numberOfTrades} is instance that contains count number of trades of the candlestick
     * **/
    private final int numberOfTrades;

    /**
     * {@code takerBuyBaseAssetVolume} is instance that contains taker buy base asset volume of the candlestick
     * **/
    private final double takerBuyBaseAssetVolume;

    /**
     * {@code takerBuyQuoteAssetVolume} is instance that contains taker buy quote asset volume of the candlestick
     * **/
    private final double takerBuyQuoteAssetVolume;

    /**
     * {@code valueToIgnore} is instance that contains value to ignore of the candlestick
     * **/
    private final double valueToIgnore;

    /** Constructor to init {@link Candlestick} object
     * @param openTime: open time of the candlestick
     * @param open: open price of the candlestick
     * @param high: high price of the candlestick
     * @param low: low price of the candlestick
     * @param close: close price of the candlestick
     * @param volume: volume of the candlestick
     * @param closeTime: close time of the candlestick
     * @param quoteAssetVolume: quote asset volume of the candlestick
     * @param numberOfTrades: number of trades of the candlestick
     * @param takerBuyBaseAssetVolume: taker buy base asset volume of the candlestick
     * @param takerBuyQuoteAssetVolume: taker buy quote asset volume of the candlestick
     * @param valueToIgnore: value to ignore of the candlestick
     * **/
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

    @Override
    public String toString() {
        return "Candlestick{" +
                "openTime=" + openTime +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                ", volume=" + volume +
                ", closeTime=" + closeTime +
                ", quoteAssetVolume=" + quoteAssetVolume +
                ", numberOfTrades=" + numberOfTrades +
                ", takerBuyBaseAssetVolume=" + takerBuyBaseAssetVolume +
                ", takerBuyQuoteAssetVolume=" + takerBuyQuoteAssetVolume +
                ", valueToIgnore=" + valueToIgnore +
                '}';
    }

}
