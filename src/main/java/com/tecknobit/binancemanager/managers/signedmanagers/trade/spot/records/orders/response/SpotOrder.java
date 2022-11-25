package com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.response;

import com.tecknobit.binancemanager.managers.signedmanagers.trade.common.Order;
import org.json.JSONObject;

/**
 * The {@code SpotOrder} class is useful to format a {@code "Binance"}'s spot order
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#spot-account-trade">
 * Spot Account/Trade</a>
 * @see Order
 **/
public abstract class SpotOrder extends Order {

    /**
     * {@code orderListId} is instance that memorizes order list identifier
     **/
    protected final long orderListId;

    /**
     * Constructor to init {@link SpotOrder} object
     *
     * @param symbol:        symbol used in the order
     * @param orderId:       order identifier
     * @param clientOrderId: client order identifier
     * @param orderListId:   list order identifier
     **/
    public SpotOrder(String symbol, long orderId, long orderListId, String clientOrderId) {
        super(symbol, orderId, clientOrderId);
        this.orderListId = orderListId;
    }

    /**
     * Constructor to init {@link SpotOrder} object
     *
     * @param spotOrder: spot order details as {@link JSONObject}
     **/
    public SpotOrder(JSONObject spotOrder) {
        super(spotOrder);
        orderListId = hOrder.getInt("orderListId");
    }

    /**
     * Method to get {@link #orderListId} instance <br>
     * Any params required
     *
     * @return {@link #orderListId} instance as long
     **/
    public long getOrderListId() {
        return orderListId;
    }

}
