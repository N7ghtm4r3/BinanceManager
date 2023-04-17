package com.tecknobit.binancemanager.managers.marketstreams.records.ticker;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.marketstreams.records.WbsMarketItem;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code WbsRollingWindowTicker} class is useful to format a websocket rolling window ticker
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#individual-symbol-rolling-window-statistics-streams">
 * Individual Symbol Rolling Window Statistics Streams</a>
 * @see BinanceItem
 * @see BinanceWebsocketResponse
 * @see WbsMarketItem
 * @see WbsMiniTicker
 **/
public class WbsRollingWindowTicker extends WbsMiniTicker {

    /**
     * {@code WindowSize} list of available window sizes
     * **/
    public enum WindowSize {

        /**
         * {@code 1h} window size
         * **/
        _1h("1h"),

        /**
         * {@code 4h} window size
         * **/
        _4h("4h"),

        /**
         * {@code 1d} window size
         * **/
        _1d("1d");

        /**
         * {@code size} of the window 
         * * **/
        private final String size;

        /**
         * Constructor to init {@link WindowSize} object
         *
         * @param size : size of the window 
         **/
        WindowSize(String size) {
            this.size = size;
        }

        /**
         * Method to get {@link #size} instance <br>
         * No-any params required
         *
         * @return {@link #size} instance as {@link String}
         **/
        public String getSize() {
            return size;
        }

        /**
         * Method to get {@link #size} instance <br>
         * No-any params required
         *
         * @return {@link #size} instance as {@link String}
         **/
        @Override
        public String toString() {
            return size;
        }

    }

    /**
     * {@code priceChange} price change of the rolling window ticker
     * **/
    protected final double priceChange;

    /**
     * {@code priceChangePercent} price change percent of the rolling window ticker
     * **/
    protected final double priceChangePercent;

    /**
     * {@code lastPrice} last price of the rolling window ticker
     * **/
    protected final double lastPrice;

    /**
     * {@code weightedAveragePrice} weighted average price of the rolling window ticker
     * **/
    protected final double weightedAveragePrice;

    /**
     * {@code statisticsOpenTime} statistics open time of the rolling window ticker
     * **/
    protected final long statisticsOpenTime;

    /**
     * {@code statisticsCloseTime} statistics close time of the rolling window ticker
     * **/
    protected final long statisticsCloseTime;

    /**
     * {@code firstTradeId} first trade id of the rolling window ticker
     * **/
    protected final long firstTradeId;

    /**
     * {@code lastTradeId} last trade id of the rolling window ticker
     * **/
    protected final long lastTradeId;

    /**
     * {@code totalNumberOfTrades} total number of trades of the rolling window ticker
     * **/
    protected final int totalNumberOfTrades;

    /**
     * Constructor to init {@link WbsRollingWindowTicker} object
     *
     * @param eventType : type of the rolling window ticker
     * @param eventTime : when the rolling window ticker
     * @param symbol : symbol of the rolling window ticker
     * @param closePrice : close price of the rolling window ticker
     * @param openPrice : open price of the rolling window ticker
     * @param highPrice : high price of the rolling window ticker
     * @param lowPrice : low price of the rolling window ticker
     * @param totalTradedBaseAssetVolume : total traded base asset volume of rolling window ticker
     * @param totalTradedQuoteAssetVolume : total traded quote asset volume of rolling window ticker
     * @param priceChange : price change of the rolling window ticker
     * @param priceChangePercent : price change percent of the rolling window ticker
     * @param lastPrice : last price of the rolling window ticker
     * @param weightedAveragePrice : weighted average price of the rolling window ticker
     * @param statisticsOpenTime : statistics open time of the rolling window ticker
     * @param statisticsCloseTime : statistics close time of the rolling window ticker
     * @param firstTradeId : first trade id of the rolling window ticker
     * @param lastTradeId : last trade id of the rolling window ticker
     * @param totalNumberOfTrades : total number of trades of the rolling window ticker
     **/
    public WbsRollingWindowTicker(EventType eventType, long eventTime, String symbol, double closePrice, double openPrice,
                                  double highPrice, double lowPrice, double totalTradedBaseAssetVolume,
                                  double totalTradedQuoteAssetVolume, double priceChange, double priceChangePercent,
                                  double lastPrice, double weightedAveragePrice, long statisticsOpenTime,
                                  long statisticsCloseTime, long firstTradeId, long lastTradeId, int totalNumberOfTrades) {
        super(eventType, eventTime, symbol, closePrice, openPrice, highPrice, lowPrice, totalTradedBaseAssetVolume,
                totalTradedQuoteAssetVolume);
        this.priceChange = priceChange;
        this.priceChangePercent = priceChangePercent;
        this.lastPrice = lastPrice;
        this.weightedAveragePrice = weightedAveragePrice;
        this.statisticsOpenTime = statisticsOpenTime;
        this.statisticsCloseTime = statisticsCloseTime;
        this.firstTradeId = firstTradeId;
        this.lastTradeId = lastTradeId;
        this.totalNumberOfTrades = totalNumberOfTrades;
    }

    /**
     * Constructor to init {@link WbsRollingWindowTicker} object
     *
     * @param jWbsRollingWindow : rolling window ticker details as {@link JSONObject}
     **/
    public WbsRollingWindowTicker(JSONObject jWbsRollingWindow) {
        super(jWbsRollingWindow);
        priceChange = hItem.getDouble("p", 0);
        priceChangePercent = hItem.getDouble("P", 0);
        lastPrice = hItem.getDouble("c", 0);
        weightedAveragePrice = hItem.getDouble("w", 0);
        statisticsOpenTime = hItem.getLong("O", 0);
        statisticsCloseTime = hItem.getLong("C", 0);
        firstTradeId = hItem.getLong("F", 0);
        lastTradeId = hItem.getLong("L", 0);
        totalNumberOfTrades = hItem.getInt("n", 0);
    }

    /**
     * Method to get {@link #priceChange} instance <br>
     * No-any params required
     *
     * @return {@link #priceChange} instance as double
     **/
    public double getPriceChange() {
        return priceChange;
    }

    /**
     * Method to get {@link #priceChange} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #priceChange} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getPriceChange(int decimals) {
        return roundValue(priceChange, decimals);
    }

    /**
     * Method to get {@link #priceChangePercent} instance <br>
     * No-any params required
     *
     * @return {@link #priceChangePercent} instance as double
     **/
    public double getPriceChangePercent() {
        return priceChangePercent;
    }

    /**
     * Method to get {@link #priceChangePercent} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #priceChangePercent} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getPriceChangePercent(int decimals) {
        return roundValue(priceChangePercent, decimals);
    }

    /**
     * Method to get {@link #lastPrice} instance <br>
     * No-any params required
     *
     * @return {@link #lastPrice} instance as double
     **/
    public double getLastPrice() {
        return lastPrice;
    }

    /**
     * Method to get {@link #lastPrice} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #lastPrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getLastPrice(int decimals) {
        return roundValue(lastPrice, decimals);
    }

    /**
     * Method to get {@link #weightedAveragePrice} instance <br>
     * No-any params required
     *
     * @return {@link #weightedAveragePrice} instance as double
     **/
    public double getWeightedAveragePrice() {
        return weightedAveragePrice;
    }

    /**
     * Method to get {@link #weightedAveragePrice} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #weightedAveragePrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getWeightedAveragePrice(int decimals) {
        return roundValue(weightedAveragePrice, decimals);
    }

    /**
     * Method to get {@link #statisticsOpenTime} instance <br>
     * No-any params required
     *
     * @return {@link #statisticsOpenTime} instance as long
     **/
    public long getStatisticsOpenTime() {
        return statisticsOpenTime;
    }

    /**
     * Method to get {@link #statisticsOpenTime} instance <br>
     * No-any params required
     *
     * @return {@link #statisticsOpenTime} instance as {@link Date}
     **/
    public Date getStatisticsOpenDate() {
        return TimeFormatter.getDate(statisticsOpenTime);
    }

    /**
     * Method to get {@link #statisticsCloseTime} instance <br>
     * No-any params required
     *
     * @return {@link #statisticsCloseTime} instance as long
     **/
    public long getStatisticsCloseTime() {
        return statisticsCloseTime;
    }

    /**
     * Method to get {@link #statisticsCloseTime} instance <br>
     * No-any params required
     *
     * @return {@link #statisticsCloseTime} instance as {@link Date}
     **/
    public Date getStatisticsCloseDate() {
        return TimeFormatter.getDate(statisticsCloseTime);
    }

    /**
     * Method to get {@link #firstTradeId} instance <br>
     * No-any params required
     *
     * @return {@link #firstTradeId} instance as long
     **/
    public long getFirstTradeId() {
        return firstTradeId;
    }

    /**
     * Method to get {@link #lastTradeId} instance <br>
     * No-any params required
     *
     * @return {@link #lastTradeId} instance as long
     **/
    public long getLastTradeId() {
        return lastTradeId;
    }

    /**
     * Method to get {@link #totalNumberOfTrades} instance <br>
     * No-any params required
     *
     * @return {@link #totalNumberOfTrades} instance as int
     **/
    public int getTotalNumberOfTrades() {
        return totalNumberOfTrades;
    }

}
