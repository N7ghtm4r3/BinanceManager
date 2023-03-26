package com.tecknobit.binancemanager.managers.signedmanagers.userdatastreams.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.Date;

/**
 * The {@code UserDataStreamsUpdate} class is useful to format a user data streams update item
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#payload-account-update">
 *             Payload: Account Update</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#payload-balance-update">
 *             Payload: Balance Update</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#payload-order-update">
 *             Payload: Order Update</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 **/
public abstract class UserDataStreamsUpdate extends BinanceItem {

    /**
     * {@code EventType} list of available event types
     **/
    public enum EventType {

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
        listStatus,

    }

    /**
     * {@code eventType} event type of the update item
     **/
    protected final EventType eventType;

    /**
     * {@code eventTime} event time of the update item
     **/
    protected final long eventTime;

    /**
     * Constructor to init {@link UserDataStreamsUpdate} object
     *
     * @param eventType: event type of the update item
     * @param eventTime: event time of the update item
     **/
    public UserDataStreamsUpdate(EventType eventType, long eventTime) {
        super(null);
        this.eventType = eventType;
        this.eventTime = eventTime;
    }

    /**
     * Constructor to init {@link UserDataStreamsUpdate} object
     *
     * @param jUpdate: update details as {@link JSONObject}
     **/
    public UserDataStreamsUpdate(JSONObject jUpdate) {
        super(jUpdate);
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
