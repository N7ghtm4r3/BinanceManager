package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common.Order;

/**
 * The {@code MarginOrder} class is useful to format all MarginOrders of BinanceMarginManager
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-trade">
 *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-trade</a>
 * **/

public class MarginOrder extends Order {

    /**
     * {@code transactTime} is instance that memorizes transaction time
     * **/
    protected final long transactTime;

    /** Constructor to init {@link MarginOrder} object
     * @param symbol: symbol used in the order
     * @param orderId: order identifier
     * @param clientOrderId: client order identifier
     * @param transactTime: transaction time
     * **/
    public MarginOrder(String symbol, double orderId, String clientOrderId, long transactTime) {
        super(symbol, orderId, clientOrderId);
        this.transactTime = transactTime;
    }

    public long getTransactTime() {
        return transactTime;
    }

    @Override
    public String toString() {
        return "MarginOrder{" +
                "transactTime=" + transactTime +
                ", symbol='" + symbol + '\'' +
                ", orderId=" + orderId +
                ", clientOrderId='" + clientOrderId + '\'' +
                '}';
    }

}
