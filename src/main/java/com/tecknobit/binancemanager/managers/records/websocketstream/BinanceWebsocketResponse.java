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
         * {@code no_content} event type
         **/
        no_content("no_content"),

        /**
         * {@code nav} event type
         **/
        nav("nav"),

        /**
         * {@code kline} event type
         **/
        kline("kline"),

        /**
         * {@code outboundAccountPosition} event type
         **/
        outboundAccountPosition("outboundAccountPosition"),

        /**
         * {@code balanceUpdate} event type
         **/
        balanceUpdate("balanceUpdate"),

        /**
         * {@code executionReport} event type
         **/
        executionReport("executionReport"),

        /**
         * {@code listStatus} event type
         **/
        listStatus("listStatus"),

        aggTrade("aggTrade"),
        trade("trade"),
        _24hrMiniTicker("24hrMiniTicker"),
        _24hrTicker("24hrTicker"),
        _1hTicker("1hTicker"),
        _4hTicker("4hTicker"),
        _1dTicker("1dTicker"),
        depth("depth"),
        depthUpdate("depthUpdate");

        private final String type;

        EventType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        /**
         * Method to reach the enum constant <br>
         *
         * @param type: type to reach
         * @return enum constant as {@link EventType}
         **/
        public static EventType reachEnumConstant(String type) {
            try {
                return EventType.valueOf(type);
            } catch (Exception e) {
                try {
                    return EventType.valueOf("_" + type);
                } catch (Exception noContent) {
                    return no_content;
                }
            }
        }

        @Override
        public String toString() {
            return type;
        }

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
        eventType = EventType.reachEnumConstant(hItem.getString("e"));
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

