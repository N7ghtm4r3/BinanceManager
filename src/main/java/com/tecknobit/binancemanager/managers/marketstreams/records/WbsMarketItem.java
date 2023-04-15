package com.tecknobit.binancemanager.managers.marketstreams.records;

import com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse;
import org.json.JSONObject;

public abstract class WbsMarketItem extends BinanceWebsocketResponse {

    protected final String symbol;
    protected final double closePrice;
    protected final double openPrice;
    protected final double highPrice;
    protected final double lowPrice;

    /**
     * Constructor to init {@link BinanceWebsocketResponse} object
     *
     * @param eventType : type of the event
     * @param eventTime : when the event occurred
     **/
    public WbsMarketItem(EventType eventType, long eventTime, String symbol, double closePrice, double openPrice,
                         double highPrice, double lowPrice) {
        super(eventType, eventTime);
        this.symbol = symbol;
        this.closePrice = closePrice;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
    }

    /**
     * Constructor to init {@link BinanceWebsocketResponse} object
     *
     * @param jBinanceWebSocketResponse : Binance websocket response details as {@link JSONObject}
     **/
    public WbsMarketItem(JSONObject jBinanceWebSocketResponse) {
        super(jBinanceWebSocketResponse);
        symbol = hItem.getString("s");
        openPrice = hItem.getDouble("o", 0);
        closePrice = hItem.getDouble("c", 0);
        highPrice = hItem.getDouble("h", 0);
        lowPrice = hItem.getDouble("l", 0);
    }

    public String getSymbol() {
        return symbol;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

}
