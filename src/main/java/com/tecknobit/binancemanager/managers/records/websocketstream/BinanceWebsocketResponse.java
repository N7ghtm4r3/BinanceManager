package com.tecknobit.binancemanager.managers.records.websocketstream;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.Date;

/**
 * The {@code BinanceWebsocketResponse} class is useful to format a Binance websocket response
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see BinanceItem
 **/
public abstract class BinanceWebsocketResponse extends BinanceItem {

    /**
     * {@code EventType} list of available event types
     **/
    public enum EventType {

        /**
         * {@code nav} event type
         **/
        nav,

        /**
         * {@code kline} event type
         **/
        kline,

        /**
         * {@code outboundAccountPosition} event type
         **/
        outboundAccountPosition,

        /**
         * {@code balanceUpdate} event type
         **/
        balanceUpdate,

        /**
         * {@code executionReport} event type
         **/
        executionReport,

        /**
         * {@code listStatus} event type
         **/
        listStatus

    }

    /**
     * {@code eventType} type of the event
     **/
    protected final EventType eventType;

    /**
     * {@code eventTime} when the event occurred
     **/
    protected final long eventTime;

    /**
     * Constructor to init {@link BinanceWebsocketResponse} object
     *
     * @param eventType: type of the event
     * @param eventTime: when the event occurred
     **/
    public BinanceWebsocketResponse(EventType eventType, long eventTime) {
        super(null);
        this.eventType = eventType;
        this.eventTime = eventTime;
    }

    /**
     * Constructor to init {@link BinanceWebsocketResponse} object
     *
     * @param jBinanceWebSocketResponse: Binance websocket response details as {@link JSONObject}
     **/
    public BinanceWebsocketResponse(JSONObject jBinanceWebSocketResponse) {
        super(jBinanceWebSocketResponse);
        eventType = EventType.valueOf(hItem.getString("e"));
        eventTime = hItem.getLong("E", 0);
    }

    /**
     * Method to get {@link #eventType} instance <br>
     * No-any params required
     *
     * @return {@link #eventType} instance as {@link EventType}
     **/
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Method to get {@link #eventTime} instance <br>
     * No-any params required
     *
     * @return {@link #eventTime} instance as long
     **/
    public long getEventTime() {
        return eventTime;
    }

    /**
     * Method to get {@link #eventTime} instance <br>
     * No-any params required
     *
     * @return {@link #eventTime} instance as {@link Date}
     **/
    public Date getEventDate() {
        return TimeFormatter.getDate(eventTime);
    }

}

