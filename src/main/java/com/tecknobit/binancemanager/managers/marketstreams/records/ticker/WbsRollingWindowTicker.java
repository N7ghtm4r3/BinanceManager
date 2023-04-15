package com.tecknobit.binancemanager.managers.marketstreams.records.ticker;

import com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse;
import org.json.JSONObject;

public class WbsRollingWindowTicker extends WbsMiniTicker {

    public enum WindowSize {

        _1h("1h"),

        _4h("4h"),
        _1d("1d");

        private final String size;

        WindowSize(String size) {
            this.size = size;
        }

        public String getSize() {
            return size;
        }

        @Override
        public String toString() {
            return size;
        }

    }

    protected final double priceChange;
    protected final double priceChangePercent;
    protected final double lastPrice;
    protected final double weightedAveragePrice;
    protected final long statisticsOpenTime;
    protected final long statisticsCloseTime;
    protected final long firstTradeId;
    protected final long lastTradeId;
    protected final int totalNumberOfTrades;

    /**
     * Constructor to init {@link BinanceWebsocketResponse} object
     *
     * @param eventType                   : type of the event
     * @param eventTime                   : when the event occurred
     * @param symbol
     * @param closePrice
     * @param openPrice
     * @param highPrice
     * @param lowPrice
     * @param totalTradedBaseAssetVolume
     * @param totalTradedQuoteAssetVolume
     * @param lastPrice
     **/
    public WbsRollingWindowTicker(EventType eventType, long eventTime, String symbol, double closePrice, double openPrice,
                                  double highPrice, double lowPrice, double totalTradedBaseAssetVolume,
                                  double totalTradedQuoteAssetVolume, double priceChange, double priceChangePercent,
                                  double lastPrice, double weightedAveragePrice, long statisticsOpenTime, long statisticsCloseTime,
                                  long firstTradeId, long lastTradeId, int totalNumberOfTrades) {
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
     * Constructor to init {@link BinanceWebsocketResponse} object
     *
     * @param jWbsRollingWindow : Binance websocket response details as {@link JSONObject}
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

    public double getPriceChange() {
        return priceChange;
    }

    public double getPriceChangePercent() {
        return priceChangePercent;
    }

    public double getLastPrice() {
        return lastPrice;
    }

    public double getWeightedAveragePrice() {
        return weightedAveragePrice;
    }

    public long getStatisticsOpenTime() {
        return statisticsOpenTime;
    }

    public long getStatisticsCloseTime() {
        return statisticsCloseTime;
    }

    public long getFirstTradeId() {
        return firstTradeId;
    }

    public long getLastTradeId() {
        return lastTradeId;
    }

    public int getTotalNumberOfTrades() {
        return totalNumberOfTrades;
    }

}
