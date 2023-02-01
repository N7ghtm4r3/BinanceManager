package com.tecknobit.binancemanager.managers.market.records.stats;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code Candlestick} class is useful to format a candlestick object
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data">
 *            Kline/Candlestick Data</a>
 *     </li>
 *      <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#uiklines">
 *           UIKlines</a>
 *     </li>
 * </ul>
 * @author N7ghtm4r3 - Tecknobit
 * **/
public class Candlestick {

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
     **/
    private final double takerBuyQuoteAssetVolume;

    /**
     * {@code valueToIgnore} is instance that contains value to ignore of the candlestick
     **/
    private final double valueToIgnore;

    /**
     * Constructor to init {@link Candlestick} object
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

    /**
     * Constructor to init {@link Candlestick} object
     *
     * @param candlestick: candlestick details as {@link JSONArray}
     **/
    public Candlestick(JSONArray candlestick) {
        this(candlestick.getLong(0), candlestick.getDouble(1), candlestick.getDouble(2),
                candlestick.getDouble(3), candlestick.getDouble(4), candlestick.getDouble(5),
                candlestick.getLong(6), candlestick.getDouble(7), candlestick.getInt(8),
                candlestick.getDouble(9), candlestick.getDouble(10), candlestick.getDouble(11));
    }

    /**
     * Method to get {@link #openTime} instance <br>
     * No-any params required
     *
     * @return {@link #openTime} instance as long
     **/
    public long getOpenTime() {
        return openTime;
    }

    /**
     * Method to get {@link #openTime} instance <br>
     * No-any params required
     *
     * @return {@link #openTime} instance as {@link Date}
     **/
    public Date getOpenDate() {
        return TimeFormatter.getDate(openTime);
    }

    /**
     * Method to get {@link #open} instance <br>
     * No-any params required
     *
     * @return {@link #open} instance as double
     **/
    public double getOpen() {
        return open;
    }

    /**
     * Method to get {@link #open} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #open} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getOpen(int decimals) {
        return roundValue(open, decimals);
    }

    /**
     * Method to get {@link #high} instance <br>
     * No-any params required
     *
     * @return {@link #high} instance as double
     **/
    public double getHigh() {
        return high;
    }

    /**
     * Method to get {@link #high} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #high} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getHigh(int decimals) {
        return roundValue(high, decimals);
    }

    /**
     * Method to get {@link #low} instance <br>
     * No-any params required
     *
     * @return {@link #low} instance as double
     **/
    public double getLow() {
        return low;
    }

    /**
     * Method to get {@link #low} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #low} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getLow(int decimals) {
        return roundValue(low, decimals);
    }

    /**
     * Method to get {@link #close} instance <br>
     * No-any params required
     *
     * @return {@link #close} instance as double
     **/
    public double getClose() {
        return close;
    }

    /**
     * Method to get {@link #close} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #close} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getClose(int decimals) {
        return roundValue(close, decimals);
    }

    /**
     * Method to get {@link #volume} instance <br>
     * No-any params required
     *
     * @return {@link #volume} instance as double
     **/
    public double getVolume() {
        return volume;
    }

    /**
     * Method to get {@link #volume} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #volume} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getVolume(int decimals) {
        return roundValue(volume, decimals);
    }

    /**
     * Method to get {@link #closeTime} instance <br>
     * No-any params required
     *
     * @return {@link #closeTime} instance as long
     **/
    public long getCloseTime() {
        return closeTime;
    }

    /**
     * Method to get {@link #quoteAssetVolume} instance <br>
     * No-any params required
     *
     * @return {@link #quoteAssetVolume} instance as double
     **/
    public double getQuoteAssetVolume() {
        return quoteAssetVolume;
    }

    /**
     * Method to get {@link #quoteAssetVolume} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #quoteAssetVolume} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getQuoteAssetVolume(int decimals) {
        return roundValue(quoteAssetVolume, decimals);
    }

    /**
     * Method to get {@link #numberOfTrades} instance <br>
     * No-any params required
     *
     * @return {@link #numberOfTrades} instance as int
     **/
    public int getNumberOfTrades() {
        return numberOfTrades;
    }

    /**
     * Method to get {@link #takerBuyBaseAssetVolume} instance <br>
     * No-any params required
     *
     * @return {@link #takerBuyBaseAssetVolume} instance as double
     **/
    public double getTakerBuyBaseAssetVolume() {
        return takerBuyBaseAssetVolume;
    }

    /**
     * Method to get {@link #takerBuyBaseAssetVolume} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #takerBuyBaseAssetVolume} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTakerBuyBaseAssetVolume(int decimals) {
        return roundValue(takerBuyBaseAssetVolume, decimals);
    }

    /**
     * Method to get {@link #takerBuyQuoteAssetVolume} instance <br>
     * No-any params required
     *
     * @return {@link #takerBuyQuoteAssetVolume} instance as double
     **/
    public double getTakerBuyQuoteAssetVolume() {
        return takerBuyQuoteAssetVolume;
    }

    /**
     * Method to get {@link #takerBuyQuoteAssetVolume} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #takerBuyQuoteAssetVolume} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTakerBuyQuoteAssetVolume(int decimals) {
        return roundValue(takerBuyQuoteAssetVolume, decimals);
    }

    /**
     * Method to get {@link #valueToIgnore} instance <br>
     * No-any params required
     *
     * @return {@link #valueToIgnore} instance as double
     **/
    public double getValueToIgnore() {
        return valueToIgnore;
    }

    /**
     * Method to get {@link #valueToIgnore} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #valueToIgnore} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getValueToIgnore(int decimals) {
        return roundValue(valueToIgnore, decimals);
    }

    /**
     * Returns a string representation of the object <br>
     * No-any params required
     *
     * @return a string representation of the object as {@link String}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

    /**
     * {@code Interval} list of intervals available for a candlestick
     **/
    public enum Interval {

        /**
         * {@code "_1s"} one second interval
         **/
        _1s("1s"),

        /**
         * {@code "_1m"} one minute interval
         **/
        _1m("1m"),

        /**
         * {@code "_3m"} three minutes interval
         **/
        _3m("3m"),

        /**
         * {@code "_5m"} five minutes interval
         **/
        _5m("5m"),

        /**
         * {@code "_15m"} fifteen minutes interval
         **/
        _15m("15m"),

        /**
         * {@code "_1h"} one hour interval
         **/
        _1h("1h"),

        /**
         * {@code "_2h"} two hours interval
         **/
        _2h("2h"),

        /**
         * {@code "_4h"} four hours interval
         **/
        _4h("4h"),

        /**
         * {@code "_6h"} six hours interval
         **/
        _6h("6h"),

        /**
         * {@code "_8h"} eight hours interval
         **/
        _8h("8h"),

        /**
         * {@code "_12h"} twelve hours interval
         **/
        _12h("12h"),

        /**
         * {@code "_1d"} one day interval
         **/
        _1d("1d"),

        /**
         * {@code "_3d"} three days interval
         **/
        _3d("3d"),

        /**
         * {@code "_1w"} one week interval
         **/
        _1w("1w"),

        /**
         * {@code "_1M"} one month interval
         **/
        _1M("1M");

        /**
         * {@code interval} interval type
         **/
        private final String interval;

        /**
         * Constructor to init {@link Interval}
         *
         * @param interval: interval type
         **/
        Interval(String interval) {
            this.interval = interval;
        }

        /**
         * Method to get {@link #interval} instance <br>
         * No-any params required
         *
         * @return {@link #interval} instance as {@link String}
         **/
        @Override
        public String toString() {
            return interval;
        }

    }

}
