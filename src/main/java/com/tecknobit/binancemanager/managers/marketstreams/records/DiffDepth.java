package com.tecknobit.binancemanager.managers.marketstreams.records;

import com.tecknobit.binancemanager.managers.market.records.OrderBook.BookOrderDetails;
import com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.market.records.OrderBook.returnOrdersList;

public class DiffDepth extends BinanceWebsocketResponse {

    private final String symbol;
    private final long firstUpdateIdInEvent;
    private final long finalUpdateIdInEvent;
    private final ArrayList<BookOrderDetails> bids;
    private final ArrayList<BookOrderDetails> asks;

    /**
     * Constructor to init {@link BinanceWebsocketResponse} object
     *
     * @param eventType : type of the event
     * @param eventTime : when the event occurred
     **/
    public DiffDepth(EventType eventType, long eventTime, String symbol, long firstUpdateIdInEvent,
                     long finalUpdateIdInEvent, ArrayList<BookOrderDetails> bids, ArrayList<BookOrderDetails> asks) {
        super(eventType, eventTime);
        this.symbol = symbol;
        this.firstUpdateIdInEvent = firstUpdateIdInEvent;
        this.finalUpdateIdInEvent = finalUpdateIdInEvent;
        this.bids = bids;
        this.asks = asks;
    }

    /**
     * Constructor to init {@link BinanceWebsocketResponse} object
     *
     * @param jDiffDepth : Binance websocket response details as {@link JSONObject}
     **/
    public DiffDepth(JSONObject jDiffDepth) {
        super(jDiffDepth);
        symbol = hItem.getString("s");
        firstUpdateIdInEvent = hItem.getLong("U", 0);
        finalUpdateIdInEvent = hItem.getLong("u", 0);
        bids = returnOrdersList(hItem.getJSONArray("b"));
        asks = returnOrdersList(hItem.getJSONArray("a"));
    }

    public String getSymbol() {
        return symbol;
    }

    public long getFirstUpdateIdInEvent() {
        return firstUpdateIdInEvent;
    }

    public long getFinalUpdateIdInEvent() {
        return finalUpdateIdInEvent;
    }

    public ArrayList<BookOrderDetails> getBids() {
        return bids;
    }

    public ArrayList<BookOrderDetails> getAsks() {
        return asks;
    }

}
