package com.tecknobit.binancemanager.managers.marketstreams.records.trade;

import com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse;
import org.json.JSONObject;

public abstract class WbsTradeStructure extends BinanceWebsocketResponse {

    protected final String symbol;
    protected final double price;
    protected final double quantity;
    protected final long tradeTime;
    protected final boolean isBuyerMarketMaker;

    /**
     * Constructor to init {@link BinanceWebsocketResponse} object
     *
     * @param eventType : type of the event
     * @param eventTime : when the event occurred
     **/
    public WbsTradeStructure(EventType eventType, long eventTime, String symbol, double price, double quantity,
                             long tradeTime, boolean isBuyerMarketMaker) {
        super(eventType, eventTime);
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
        this.tradeTime = tradeTime;
        this.isBuyerMarketMaker = isBuyerMarketMaker;
    }

    /**
     * Constructor to init {@link BinanceWebsocketResponse} object
     *
     * @param jBinanceWebSocketResponse : Binance websocket response details as {@link JSONObject}
     **/
    public WbsTradeStructure(JSONObject jBinanceWebSocketResponse) {
        super(jBinanceWebSocketResponse);
        symbol = hItem.getString("s");
        price = hItem.getDouble("p", 0);
        quantity = hItem.getDouble("q", 0);
        tradeTime = hItem.getLong("T", 0);
        isBuyerMarketMaker = hItem.getBoolean("m");
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public double getQuantity() {
        return quantity;
    }

    public long getTradeTime() {
        return tradeTime;
    }

    public boolean isBuyerMarketMaker() {
        return isBuyerMarketMaker;
    }

}
