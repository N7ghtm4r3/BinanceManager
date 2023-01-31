package com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.response;

import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

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
     * {@code preventedMatchId} prevented match identifier
     **/
    protected final long preventedMatchId;

    /**
     * {@code preventedQuantity} prevented quantity value
     **/
    protected final double preventedQuantity;

    /**
     * Constructor to init {@link SpotOrder} object
     *
     * @param symbol:            symbol used in the order
     * @param orderId:           order identifier
     * @param clientOrderId:     client order identifier
     * @param orderListId:       list order identifier
     * @param preventedMatchId:  prevented match identifier
     * @param preventedQuantity: prevented quantity value
     **/
    public SpotOrder(String symbol, long orderId, String clientOrderId, long orderListId, long preventedMatchId,
                     double preventedQuantity) {
        super(symbol, orderId, clientOrderId);
        this.orderListId = orderListId;
        this.preventedMatchId = preventedMatchId;
        this.preventedQuantity = preventedQuantity;
    }

    /**
     * Constructor to init {@link SpotOrder} object
     *
     * @param spotOrder: spot order details as {@link JSONObject}
     **/
    public SpotOrder(JSONObject spotOrder) {
        super(spotOrder);
        orderListId = hOrder.getLong("orderListId");
        preventedMatchId = hOrder.getLong("preventedMatchId", 0);
        preventedQuantity = hOrder.getDouble("preventedQuantity", 0);
    }

    /**
     * Method to get {@link #orderListId} instance <br>
     * No-any params required
     *
     * @return {@link #orderListId} instance as long
     **/
    public long getOrderListId() {
        return orderListId;
    }

    /**
     * Method to get {@link #preventedMatchId} instance <br>
     * No-any params required
     *
     * @return {@link #preventedMatchId} instance as long
     **/
    public long getPreventedMatchId() {
        return preventedMatchId;
    }

    /**
     * Method to get {@link #preventedQuantity} instance <br>
     * No-any params required
     *
     * @return {@link #preventedQuantity} instance as double
     **/
    public double getPreventedQuantity() {
        return preventedQuantity;
    }

    /**
     * Method to get {@link #preventedQuantity} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #preventedQuantity} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getPreventedQuantity(int decimals) {
        return roundValue(preventedQuantity, decimals);
    }

}
