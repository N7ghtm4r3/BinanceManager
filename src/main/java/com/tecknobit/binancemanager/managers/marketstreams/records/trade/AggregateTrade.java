package com.tecknobit.binancemanager.managers.marketstreams.records.trade;

import com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse;
import org.json.JSONObject;

public class AggregateTrade extends WbsTradeStructure {

    private final long aggregateTradeId;
    private final long firstTradeId;
    private final long lastTradeId;

    /**
     * Constructor to init {@link BinanceWebsocketResponse} object
     *
     * @param eventType          : type of the event
     * @param eventTime          : when the event occurred
     * @param symbol
     * @param price
     * @param quantity
     * @param tradeTime
     * @param isBuyerMarketMaker
     **/
    public AggregateTrade(EventType eventType, long eventTime, String symbol, double price, double quantity,
                          long tradeTime, boolean isBuyerMarketMaker, long aggregateTradeId, long firstTradeId,
                          long lastTradeId) {
        super(eventType, eventTime, symbol, price, quantity, tradeTime, isBuyerMarketMaker);
        this.aggregateTradeId = aggregateTradeId;
        this.firstTradeId = firstTradeId;
        this.lastTradeId = lastTradeId;
    }

    /**
     * Constructor to init {@link BinanceWebsocketResponse} object
     *
     * @param jBinanceWebSocketResponse : Binance websocket response details as {@link JSONObject}
     **/
    public AggregateTrade(JSONObject jBinanceWebSocketResponse) {
        super(jBinanceWebSocketResponse);
        aggregateTradeId = hItem.getLong("a", 0);
        firstTradeId = hItem.getLong("f", 0);
        lastTradeId = hItem.getLong("l", 0);
    }

    public long getAggregateTradeId() {
        return aggregateTradeId;
    }

    public long getFirstTradeId() {
        return firstTradeId;
    }

    public long getLastTradeId() {
        return lastTradeId;
    }

}
