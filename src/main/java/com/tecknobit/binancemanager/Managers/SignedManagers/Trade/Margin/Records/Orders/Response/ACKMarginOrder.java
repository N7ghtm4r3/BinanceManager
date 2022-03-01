package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Response;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.MarginOrder;

public class ACKMarginOrder extends MarginOrder {

    private final boolean isIsolated;

    public ACKMarginOrder(String symbol, double orderId, String clientOrderId, long transactTime, boolean isIsolated) {
        super(symbol, orderId, clientOrderId, transactTime);
        this.isIsolated = isIsolated;
    }

    public boolean isIsolated() {
        return isIsolated;
    }

}
