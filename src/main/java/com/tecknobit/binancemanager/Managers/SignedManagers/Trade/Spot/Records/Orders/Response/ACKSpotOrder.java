package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Response;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.SpotOrder;

/**
 *  The {@code ACKOrder} class is useful to format all SpotOrder Binance request in ACK format
 *  @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
 *      https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class ACKSpotOrder extends SpotOrder {

    /**
     * {@code transactTime} is instance that memorizes transaction time
     * **/
    protected final long transactTime;

    /** Constructor to init {@link ACKSpotOrder} object
     * @param symbol: symbol used in the order
     * @param orderId: order identifier
     * @param clientOrderId: client order identifier
     * @param orderListId: list order identifier
     * @param transactTime: transaction time
     * **/
    public ACKSpotOrder(String symbol, long orderId, long orderListId, String clientOrderId, long transactTime) {
        super(symbol, orderId, orderListId, clientOrderId);
        this.transactTime = transactTime;
    }

    public long getTransactTime() {
        return transactTime;
    }

    @Override
    public String toString() {
        return "ACKSpotOrder{" +
                "transactTime=" + transactTime +
                ", orderListId=" + orderListId +
                ", symbol='" + symbol + '\'' +
                ", orderId=" + orderId +
                ", clientOrderId='" + clientOrderId + '\'' +
                '}';
    }

}
