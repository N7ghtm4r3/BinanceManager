package com.tecknobit.binancemanager.managers.signedmanagers.convert.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.Date;

/**
 * The {@code AcceptQuote} class is useful to format an accept quote
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#accept-quote-trade">
 * Accept Quote (TRADE)</a>
 * @see BinanceItem
 **/
public class AcceptQuote extends BinanceItem {

    /**
     * {@code AcceptQuoteStatus} list of available accept quote statuses
     **/
    public enum AcceptQuoteStatus {

        /**
         * {@code PROCESS} accept quote status
         **/
        PROCESS,

        /**
         * {@code ACCEPT_SUCCESS} accept quote status
         **/
        ACCEPT_SUCCESS,

        /**
         * {@code SUCCESS} accept quote status
         **/
        SUCCESS,

        /**
         * {@code FAIL} accept quote status
         **/
        FAIL

    }

    /**
     * {@code orderId} id of the quote
     **/
    protected final long orderId;

    /**
     * {@code createTime} creation time of the quote
     **/
    protected final long createTime;

    /**
     * {@code orderStatus} status of the quote
     **/
    protected final AcceptQuoteStatus orderStatus;

    /**
     * Constructor to init {@link AcceptQuote} object
     *
     * @param orderId:     id of the quote
     * @param createTime:  creation time of the quote
     * @param orderStatus: status of the quote
     **/
    public AcceptQuote(long orderId, long createTime, AcceptQuoteStatus orderStatus) {
        super(null);
        this.orderId = orderId;
        this.createTime = createTime;
        this.orderStatus = orderStatus;
    }

    /**
     * Constructor to init {@link AcceptQuote} object
     *
     * @param jAcceptQuote: quote details as {@link JSONObject}
     **/
    public AcceptQuote(JSONObject jAcceptQuote) {
        super(jAcceptQuote);
        orderId = hItem.getLong("orderId", 0);
        createTime = hItem.getLong("createTime", 0);
        orderStatus = AcceptQuoteStatus.valueOf(hItem.getString("orderStatus"));
    }

    /**
     * Method to get {@link #orderId} instance <br>
     * No-any params required
     *
     * @return {@link #orderId} instance as long
     **/
    public long getOrderId() {
        return orderId;
    }

    /**
     * Method to get {@link #createTime} instance <br>
     * No-any params required
     *
     * @return {@link #createTime} instance as long
     **/
    public long getCreateTime() {
        return createTime;
    }

    /**
     * Method to get {@link #createTime} instance <br>
     * No-any params required
     *
     * @return {@link #createTime} instance as {@link Date}
     **/
    public Date getCreationDate() {
        return TimeFormatter.getDate(createTime);
    }

    /**
     * Method to get {@link #orderStatus} instance <br>
     * No-any params required
     *
     * @return {@link #orderStatus} instance as {@link AcceptQuoteStatus}
     **/
    public AcceptQuoteStatus getOrderStatus() {
        return orderStatus;
    }

}
