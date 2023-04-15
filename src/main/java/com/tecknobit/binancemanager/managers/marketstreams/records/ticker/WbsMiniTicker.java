package com.tecknobit.binancemanager.managers.marketstreams.records.ticker;

import com.tecknobit.binancemanager.managers.marketstreams.records.WbsMarketItem;
import com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse;
import org.json.JSONObject;

public class WbsMiniTicker extends WbsMarketItem {

    protected final double totalTradedBaseAssetVolume;
    protected final double totalTradedQuoteAssetVolume;

    /**
     * Constructor to init {@link BinanceWebsocketResponse} object
     *
     * @param eventType  : type of the event
     * @param eventTime  : when the event occurred
     * @param symbol
     * @param closePrice
     * @param openPrice
     * @param highPrice
     * @param lowPrice
     **/
    public WbsMiniTicker(EventType eventType, long eventTime, String symbol, double closePrice, double openPrice,
                         double highPrice, double lowPrice, double totalTradedBaseAssetVolume,
                         double totalTradedQuoteAssetVolume) {
        super(eventType, eventTime, symbol, closePrice, openPrice, highPrice, lowPrice);
        this.totalTradedBaseAssetVolume = totalTradedBaseAssetVolume;
        this.totalTradedQuoteAssetVolume = totalTradedQuoteAssetVolume;
    }

    /**
     * Constructor to init {@link BinanceWebsocketResponse} object
     *
     * @param jBinanceWebSocketResponse : Binance websocket response details as {@link JSONObject}
     **/
    public WbsMiniTicker(JSONObject jBinanceWebSocketResponse) {
        super(jBinanceWebSocketResponse);
        totalTradedBaseAssetVolume = hItem.getDouble("v", 0);
        totalTradedQuoteAssetVolume = hItem.getDouble("q", 0);
    }

    public double getTotalTradedBaseAssetVolume() {
        return totalTradedBaseAssetVolume;
    }

    public double getTotalTradedQuoteAssetVolume() {
        return totalTradedQuoteAssetVolume;
    }

}
