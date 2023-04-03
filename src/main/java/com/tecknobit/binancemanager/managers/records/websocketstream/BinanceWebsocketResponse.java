package com.tecknobit.binancemanager.managers.records.websocketstream;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public abstract class BinanceWebsocketResponse extends BinanceItem {

    public enum EventType {

        nav,
        kline

    }

    private final EventType eventType;
    private final long eventTime;

    public BinanceWebsocketResponse(EventType eventType, long eventTime) {
        super(null);
        this.eventType = eventType;
        this.eventTime = eventTime;
    }

    public BinanceWebsocketResponse(JSONObject jBinanceWebSocketResponse) {
        super(jBinanceWebSocketResponse);
        eventType = EventType.valueOf(hItem.getString("e"));
        eventTime = hItem.getLong("E", 0);
    }

    public EventType getEventType() {
        return eventType;
    }

    public long getEventTime() {
        return eventTime;
    }

}

