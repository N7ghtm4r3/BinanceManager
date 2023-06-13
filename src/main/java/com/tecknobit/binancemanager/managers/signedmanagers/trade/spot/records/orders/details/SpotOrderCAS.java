package com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.details;

import com.tecknobit.apimanager.formatters.JsonHelper;
import com.tecknobit.binancemanager.managers.BinanceManager.BinanceResponse;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.response.FullSpotOrder;
import org.json.JSONObject;

/**
 * The {@code SpotOrderCAS} class is useful to format an order cancellation and a new send of the same order response
 *
 * @author N7ghtm4r3 - Tecknobit
 * @implNote CAS means CANCEL AND SEND
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
 * Cancel an Existing Order and Send a New Order (TRADE)</a>
 * @see BinanceResponse
 */
public class SpotOrderCAS implements BinanceResponse {

    /**
     * {@code cancelResult} is instance that memorizes result of cancellation of the past order
     */
    private final String cancelResult;

    /**
     * {@code newOrderResult} is instance that memorizes result of creation of the new order
     */
    private final String newOrderResult;

    /**
     * {@code orderCanceled} is instance that memorizes order canceled details as {@link SpotOrderDetails}
     */
    private final SpotOrderDetails orderCanceled;

    /**
     * {@code newOrder} is instance that memorizes new order details as {@link FullSpotOrder}
     */
    private final FullSpotOrder newOrder;

    /**
     * {@code hSpotOrder} is instance useful to manage different responses scenarios
     */
    private final JsonHelper hSpotOrder;

    /** Constructor to init {@link SpotOrderCAS} object
     * @param cancelResult: symbol used in the order
     * @param newOrderResult: order identifier
     * @param orderCanceled: client order identifier
     * @param newOrder: list order identifier
     */
    public SpotOrderCAS(String cancelResult, String newOrderResult, SpotOrderDetails orderCanceled, FullSpotOrder newOrder) {
        this.cancelResult = cancelResult;
        this.newOrderResult = newOrderResult;
        this.orderCanceled = orderCanceled;
        this.newOrder = newOrder;
        hSpotOrder = null;
    }

    /**
     * Constructor to init {@link SpotOrderCAS} object
     *
     * @param casOrder: cancel and send order details as {@link JSONObject}
     */
    public SpotOrderCAS(JSONObject casOrder) {
        hSpotOrder = new JsonHelper(casOrder);
        cancelResult = hSpotOrder.getString("cancelResult");
        newOrderResult = hSpotOrder.getString("newOrderResult");
        orderCanceled = new SpotOrderDetails(hSpotOrder.getJSONObject("cancelResponse", new JSONObject()));
        JSONObject newOrderResponse = hSpotOrder.getJSONObject("newOrderResponse");
        if (newOrderResponse != null)
            newOrder = new FullSpotOrder(newOrderResponse);
        else
            newOrder = null;
    }

    /**
     * Method to get {@link #cancelResult} instance <br>
     * No-any params required
     *
     * @return {@link #cancelResult} instance as {@link String}
     */
    public String getCancelResult() {
        return cancelResult;
    }

    /**
     * Method to get {@link #newOrderResult} instance <br>
     * No-any params required
     *
     * @return {@link #newOrderResult} instance as {@link String}
     */
    public String getNewOrderResult() {
        return newOrderResult;
    }

    /**
     * Method to get {@link #orderCanceled} instance <br>
     * No-any params required
     *
     * @return {@link #orderCanceled} instance as {@link SpotOrderDetails}
     */
    public SpotOrderDetails getOrderCanceled() {
        return orderCanceled;
    }

    /**
     * Method to get {@link #newOrder} instance <br>
     * No-any params required
     *
     * @return {@link #newOrder} instance as {@link FullSpotOrder}
     */
    public FullSpotOrder getNewOrder() {
        return newOrder;
    }

    /**
     * Method to get error code <br>
     * No-any params required
     *
     * @return code of error as int
     * *
     * @implSpec if code error is not present in {@code "Binance"}'s response will be returned -1 as default
     */
    @Override
    public int getCode() {
        if (hSpotOrder != null)
            return hSpotOrder.getInt("code", -1);
        return -1;
    }

    /**
     * Method to get error message <br>
     * No-any params required
     *
     * @return message of error as {@link String}
     * *
     * @implSpec if message error is not present in {@code "Binance"}'s response will be returned null as default
     */
    @Override
    public String getMessage() {
        if (hSpotOrder != null)
            return hSpotOrder.getString("msg", null);
        return null;
    }

    /**
     * Returns a string representation of the object <br>
     * No-any params required
     *
     * @return a string representation of the object as {@link String}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

}
