package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.orders.response;

import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order;
import org.json.JSONObject;

/**
 * The {@code ACKMarginOrder} class is useful to format a {@code "Binance"}'s {@code "ACK"} response
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
 * Margin Account New Order (TRADE)</a>
 * @see Order
 * @see MarginOrder
 **/
public class ACKMarginOrder extends MarginOrder {

    /**
     * {@code isIsolated} is instance that memorizes if is isolated
     **/
    protected final boolean isIsolated;

    /**
     * Constructor to init {@link ACKMarginOrder} object
     *
     * @param symbol:        symbol used in the order
     * @param orderId:       order identifier
     * @param clientOrderId: client order identifier
     * @param transactTime:  transaction time
     * @param isIsolated:    is isolated
     **/
    public ACKMarginOrder(String symbol, long orderId, String clientOrderId, long transactTime, boolean isIsolated) {
        super(symbol, orderId, clientOrderId, transactTime);
        this.isIsolated = isIsolated;
    }

    /**
     * Constructor to init {@link ACKMarginOrder} object
     *
     * @param ackMarginOrder: ack margin order details as {@link JSONObject}
     **/
    public ACKMarginOrder(JSONObject ackMarginOrder) {
        super(ackMarginOrder);
        isIsolated = ackMarginOrder.getBoolean("isIsolated");
    }

    /**
     * Method to get {@link #isIsolated} instance <br>
     * No-any params required
     *
     * @return {@link #isIsolated} instance as boolean
     **/
    public boolean isIsolated() {
        return isIsolated;
    }

}
