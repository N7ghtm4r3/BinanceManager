package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common.Order;

public class MarginOrder extends Order {

    private final long transactTime;

    public MarginOrder(String symbol, double orderId, String clientOrderId, long transactTime) {
        super(symbol, orderId, clientOrderId);
        this.transactTime = transactTime;
    }

    public long getTransactTime() {
        return transactTime;
    }

}
