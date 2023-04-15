package com.tecknobit.binancemanager.managers.marketstreams.records.ticker;

import com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse;
import org.json.JSONObject;

public class WbsTicker extends WbsRollingWindowTicker {

    private final double firstTradePrice;
    private final double lastQuantity;
    private final double bestBidPrice;
    private final double bestBidQuantity;
    private final double bestAskPrice;
    private final double bestAskQuantity;

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
     * @param priceChange
     * @param priceChangePercent
     * @param lastPrice
     * @param weightedAveragePrice
     * @param statisticsOpenTime
     * @param statisticsCloseTime
     * @param firstTradeId
     * @param lastTradeId
     * @param totalNumberOfTrades
     **/
    public WbsTicker(EventType eventType, long eventTime, String symbol, double closePrice, double openPrice,
                     double highPrice, double lowPrice, double totalTradedBaseAssetVolume,
                     double totalTradedQuoteAssetVolume, double priceChange, double priceChangePercent, double lastPrice,
                     double weightedAveragePrice, long statisticsOpenTime, long statisticsCloseTime, long firstTradeId,
                     long lastTradeId, int totalNumberOfTrades, double firstTradePrice, double lastQuantity,
                     double bestBidPrice, double bestBidQuantity, double bestAskPrice, double bestAskQuantity) {
        super(eventType, eventTime, symbol, closePrice, openPrice, highPrice, lowPrice, totalTradedBaseAssetVolume,
                totalTradedQuoteAssetVolume, priceChange, priceChangePercent, lastPrice, weightedAveragePrice,
                statisticsOpenTime, statisticsCloseTime, firstTradeId, lastTradeId, totalNumberOfTrades);
        this.firstTradePrice = firstTradePrice;
        this.lastQuantity = lastQuantity;
        this.bestBidPrice = bestBidPrice;
        this.bestBidQuantity = bestBidQuantity;
        this.bestAskPrice = bestAskPrice;
        this.bestAskQuantity = bestAskQuantity;
    }

    /**
     * Constructor to init {@link BinanceWebsocketResponse} object
     *
     * @param jWbsTicker : Binance websocket response details as {@link JSONObject}
     **/
    public WbsTicker(JSONObject jWbsTicker) {
        super(jWbsTicker);
        firstTradePrice = hItem.getDouble("x", 0);
        lastQuantity = hItem.getDouble("Q", 0);
        bestBidPrice = hItem.getDouble("b", 0);
        bestBidQuantity = hItem.getDouble("B", 0);
        bestAskPrice = hItem.getDouble("a", 0);
        bestAskQuantity = hItem.getDouble("A", 0);
    }

    public double getLastQuantity() {
        return lastQuantity;
    }

    public double getBestBidPrice() {
        return bestBidPrice;
    }

    public double getBestBidQuantity() {
        return bestBidQuantity;
    }

    public double getBestAskPrice() {
        return bestAskPrice;
    }

    public double getBestAskQuantity() {
        return bestAskQuantity;
    }

}
