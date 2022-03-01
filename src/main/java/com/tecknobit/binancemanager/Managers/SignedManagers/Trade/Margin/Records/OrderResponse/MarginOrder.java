package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.OrderResponse;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Order;

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
