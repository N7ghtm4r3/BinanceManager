package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Response;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.SpotOrder;

/**
 *  The {@code ACKOrder} class is useful to format all SpotOrder Binance request in ACK format
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class ACKSpotOrder extends SpotOrder {

    private final long transactTime;

    public ACKSpotOrder(String symbol, long orderId, long orderListId, String clientOrderId, long transactTime) {
        super(symbol, orderId, orderListId, clientOrderId);
        this.transactTime = transactTime;
    }

    public long getTransactTime() {
        return transactTime;
    }

}
