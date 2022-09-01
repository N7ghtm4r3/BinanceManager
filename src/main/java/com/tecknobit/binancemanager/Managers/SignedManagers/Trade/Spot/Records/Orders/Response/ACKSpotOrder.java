package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Response;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.SpotOrder;
import org.json.JSONObject;

/**
 * The {@code ACKOrder} class is useful to format all SpotOrder Binance request in ACK format
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
 * https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
 **/

public class ACKSpotOrder extends SpotOrder {

    /**
     * {@code STOP_ON_FAILURE_REPLACE_MODE} is constant to use if the cancel request fails, the new order placement will not be attempted
     * **/
    public static final String STOP_ON_FAILURE_REPLACE_MODE = "STOP_ON_FAILURE";

    /**
     * {@code STOP_ON_FAILURE_REPLACE_MODE} is constant to use when new order placement will be attempted even if cancel request fails
     * **/
    public static final String ALLOW_FAILURE_REPLACE_MODE = "ALLOW_FAILURE";

    /**
     * {@code transactTime} is instance that memorizes transaction time
     **/
    protected long transactTime;

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

    /** Constructor to init {@link ACKSpotOrder} object
     * @param ackOrder: ack order details as {@link JSONObject}
     * **/
    public ACKSpotOrder(JSONObject ackOrder) {
        super(ackOrder);
        transactTime = hOrder.getLong("transactTime");
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
