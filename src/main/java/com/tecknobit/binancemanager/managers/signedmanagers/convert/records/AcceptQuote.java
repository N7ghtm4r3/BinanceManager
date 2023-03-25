package com.tecknobit.binancemanager.managers.signedmanagers.convert.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class AcceptQuote extends BinanceItem {

    public enum AcceptQuoteStatus {

        PROCESS,
        ACCEPT_SUCCESS,
        SUCCESS,
        FAIL

    }

    protected final long orderId;
    protected final long createTime;
    protected final AcceptQuoteStatus orderStatus;

    public AcceptQuote(long orderId, long createTime, AcceptQuoteStatus orderStatus) {
        super(null);
        this.orderId = orderId;
        this.createTime = createTime;
        this.orderStatus = orderStatus;
    }

    public AcceptQuote(JSONObject jAcceptQuote) {
        super(jAcceptQuote);
        orderId = hItem.getLong("orderId", 0);
        createTime = hItem.getLong("createTime", 0);
        orderStatus = AcceptQuoteStatus.valueOf(hItem.getString("orderStatus"));
    }

    public long getOrderId() {
        return orderId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public AcceptQuoteStatus getOrderStatus() {
        return orderStatus;
    }

}
