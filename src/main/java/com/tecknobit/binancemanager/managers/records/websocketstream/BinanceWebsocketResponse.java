package com.tecknobit.binancemanager.managers.records.websocketstream;

import com.tecknobit.apimanager.annotations.Structure;
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
@Structure
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

        /**
         * {@code aggTrade} event type
         **/
        aggTrade("aggTrade"),

        /**
         * {@code trade} event type
         **/
        trade("trade"),

        /**
         * {@code _24hrMiniTicker} event type
         **/
        _24hrMiniTicker("24hrMiniTicker"),

        /**
         * {@code _24hrTicker} event type
         **/
        _24hrTicker("24hrTicker"),

        /**
         * {@code _1hTicker} event type
         **/
        _1hTicker("1hTicker"),

        /**
         * {@code _4hTicker} event type
         **/
        _4hTicker("4hTicker"),

        /**
         * {@code _1dTicker} event type
         **/
        _1dTicker("1dTicker"),

        /**
         * {@code depth} event type
         **/
        depth("depth"),

        /**
         * {@code depthUpdate} event type
         **/
        depthUpdate("depthUpdate");

        /**
         * {@code type} of the event
         **/
        private final String type;

        /**
         * Constructor to init {@link EventType} object
         *
         * @param type: type of the event
         **/
        EventType(String type) {
            this.type = type;
        }

        /**
         * Method to get {@link #type} instance <br>
         * No-any params required
         *
         * @return {@link #type} instance as {@link String}
         **/
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

        /**
         * Method to get {@link #type} instance <br>
         * No-any params required
         *
         * @return {@link #type} instance as {@link String}
         **/
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

