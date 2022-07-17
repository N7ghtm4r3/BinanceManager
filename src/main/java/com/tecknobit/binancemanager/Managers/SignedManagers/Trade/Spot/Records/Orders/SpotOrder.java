package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common.Order;

/**
 *  The {@code SpotOrder} class is useful to manage all SpotOrder Binance request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#spot-account-trade">
 *      https://binance-docs.github.io/apidocs/spot/en/#spot-account-trade</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class SpotOrder extends Order {

    /**
     * {@code orderListId} is instance that memorizes order list identifier
     * **/
    protected final long orderListId;

    /** Constructor to init {@link SpotOrder} object
     * @param symbol: symbol used in the order
     * @param orderId: order identifier
     * @param clientOrderId: client order identifier
     * @param orderListId: list order identifier
     * **/
    public SpotOrder(String symbol, double orderId, long orderListId, String clientOrderId) {
        super(symbol, orderId, clientOrderId);
        this.orderListId = orderListId;
    }

    public long getOrderListId() {
        return orderListId;
    }

}
