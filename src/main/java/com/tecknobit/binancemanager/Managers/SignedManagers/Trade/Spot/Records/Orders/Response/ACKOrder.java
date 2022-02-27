package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Response;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Order;

/**
 *  The {@code ACKOrder} class is useful to format all Order Binance request in ACK format
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#new-order-trade
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class ACKOrder extends Order {

    private final long transactTime;

    public ACKOrder(String symbol, long orderId, long orderListId, String clientOrderId, long transactTime) {
        super(symbol, orderId, orderListId, clientOrderId);
        this.transactTime = transactTime;
    }

    public long getTransactTime() {
        return transactTime;
    }

}
