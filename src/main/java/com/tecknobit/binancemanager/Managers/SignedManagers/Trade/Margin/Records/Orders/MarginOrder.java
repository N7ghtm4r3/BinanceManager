package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common.Order;

/**
 * The {@code MarginOrder} class is useful to format all MarginOrders of BinanceMarginManager
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-trade">https://binance-docs.github.io/apidocs/spot/en/#margin-account-trade</a>
 * **/

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
