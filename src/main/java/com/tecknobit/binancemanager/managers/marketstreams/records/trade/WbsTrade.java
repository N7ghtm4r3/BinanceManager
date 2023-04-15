package com.tecknobit.binancemanager.managers.marketstreams.records.trade;

import com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse;
import org.json.JSONObject;

public class WbsTrade extends WbsTradeStructure {

    private final long tradeId;
    private final long buyerOrderId;
    private final long sellerOrderId;

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
    public WbsTrade(EventType eventType, long eventTime, String symbol, double price, double quantity, long tradeTime,
                    boolean isBuyerMarketMaker, long tradeId, long buyerOrderId, long sellerOrderId) {
        super(eventType, eventTime, symbol, price, quantity, tradeTime, isBuyerMarketMaker);
        this.tradeId = tradeId;
        this.buyerOrderId = buyerOrderId;
        this.sellerOrderId = sellerOrderId;
    }

    /**
     * Constructor to init {@link BinanceWebsocketResponse} object
     *
     * @param jBinanceWebSocketResponse : Binance websocket response details as {@link JSONObject}
     **/
    public WbsTrade(JSONObject jBinanceWebSocketResponse) {
        super(jBinanceWebSocketResponse);
        tradeId = hItem.getLong("t", 0);
        buyerOrderId = hItem.getLong("b", 0);
        sellerOrderId = hItem.getLong("a", 0);
    }

    public long getTradeId() {
        return tradeId;
    }

    public long getBuyerOrderId() {
        return buyerOrderId;
    }

    public long getSellerOrderId() {
        return sellerOrderId;
    }

}
