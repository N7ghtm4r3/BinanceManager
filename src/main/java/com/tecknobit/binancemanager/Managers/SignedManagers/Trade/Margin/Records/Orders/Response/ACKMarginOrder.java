package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Response;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.MarginOrder;

/**
 * The {@code ACKMarginOrder} class is useful to format ACKMarginOrder object of Binance's request Margin Account New Order
 * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade
 * **/

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
