package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Response;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.MarginOrder;

/**
 * The {@code ACKMarginOrder} class is useful to format ACKMarginOrder object of Binance's request Margin Account New Order
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
 *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade</a>
 * **/

public class ACKMarginOrder extends MarginOrder {

    /**
     * {@code isIsolated} is instance that memorizes if is isolated
     * **/
    protected final boolean isIsolated;

    /** Constructor to init {@link ACKMarginOrder} object
     * @param symbol: symbol used in the order
     * @param orderId: order identifier
     * @param clientOrderId: client order identifier
     * @param transactTime: transaction time
     * @param isIsolated: is isolated
     * **/
    public ACKMarginOrder(String symbol, double orderId, String clientOrderId, long transactTime, boolean isIsolated) {
        super(symbol, orderId, clientOrderId, transactTime);
        this.isIsolated = isIsolated;
    }

    public boolean isIsolated() {
        return isIsolated;
    }

}
