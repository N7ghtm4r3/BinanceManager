package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Commons.Order;

/**
 *  The {@code SpotOrder} class is useful to manage all SpotOrder Binance request
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#spot-account-trade
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class SpotOrder extends Order {

    private final long orderListId;

    public SpotOrder(String symbol, double orderId, long orderListId, String clientOrderId) {
        super(symbol, orderId, clientOrderId);
        this.orderListId = orderListId;
    }

    public long getOrderListId() {
        return orderListId;
    }

}
