package com.tecknobit.binancemanager.managers.signedmanagers.trade.commons;

import com.tecknobit.apimanager.formatters.JsonHelper;
import org.json.JSONObject;

import static com.tecknobit.apimanager.formatters.ScientificNotationParser.sNotationParse;
import static com.tecknobit.binancemanager.managers.BinanceManager.Params;

/**
 * The {@code Order} class is useful to manage and format a {@code "Binance"}'s order
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 * <li>
 * <a href="https://binance-docs.github.io/apidocs/spot/en/#spot-account-trade">
 * Spot Account/Trade</a>
 * </li>
 * <li>
 * <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
 * New Order (TRADE)</a>
 * </li>
 * <li>
 * <a href="https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data">
 * Query Order (USER_DATA)</a>
 * </li>
 * <li>
 * <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade">
 * Cancel Order (TRADE)</a>
 * </li>
 * <li>
 * <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-trade">
 * Margin Account/Trade</a>
 * </li>
 * <li>
 * <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
 * Margin Account New Order (TRADE)</a>
 * </li>
 * <li>
 * <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data">
 * Query Cross Margin Account Details (USER_DATA)</a>
 * </li>
 * </ul>
 **/
public class Order {

    /**
     * {@code orderId} is instance that memorizes order identifier
     **/
    protected final long orderId;

    /**
     * {@code symbol} is instance that memorizes symbol used in the order
     **/
    protected final String symbol;

    /**
     * {@code clientOrderId} is instance that memorizes client order identifier
     **/
    protected final String clientOrderId;

    /**
     * {@code jsonHelper} is instance that memorizes {@link JsonHelper} tool
     **/
    protected final JsonHelper hOrder;

    /**
     * Constructor to init {@link Order} object
     *
     * @param symbol:        symbol used in the order
     * @param orderId:       order identifier
     * @param clientOrderId: client order identifier
     **/
    public Order(String symbol, long orderId, String clientOrderId) {
        this.symbol = symbol;
        this.orderId = orderId;
        this.clientOrderId = clientOrderId;
        hOrder = null;
    }

    /**
     * Constructor to init {@link Order} object
     *
     * @param order: order details as {@link JSONObject}
     **/
    public Order(JSONObject order) {
        hOrder = new JsonHelper(order);
        symbol = hOrder.getString("symbol");
        orderId = hOrder.getLong("orderId");
        clientOrderId = hOrder.getString("clientOrderId");
    }

    /**
     * Method to assemble a payload for limit order request
     *
     * @param timeInForce: time in force for the order
     * @param quantity:    quantity value in the order
     * @param price:       price value in the order
     * @param extraParams: extraParams of the request
     * @return payload request as {@link Params}
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official {@code "Binance"}'s documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade">
     * Test New Order (TRADE)</a>
     **/
    public static Params getLimitPayload(TimeInForce timeInForce, double quantity, double price, Params extraParams) {
        Params payload = new Params();
        payload.addParam("timeInForce", timeInForce);
        payload.addParam("quantity", sNotationParse(8, quantity));
        payload.addParam("price", price);
        if (extraParams != null)
            payload.mergeParams(extraParams);
        return payload;
    }

    /**
     * Method to assemble a payload for market order request
     *
     * @param keyQty:      key for qty value (quantity or quoteOrderQty)
     * @param qty:         quantity value in the order
     * @param extraParams: extraParams of the request
     * @return payload request as {@link Params}
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official {@code "Binance"}'s documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade">
     * Test New Order (TRADE)</a>
     **/
    public static Params getMarketPayload(String keyQty, double qty, Params extraParams) {
        Params payload = new Params();
        payload.addParam(keyQty, sNotationParse(8, qty));
        if (extraParams != null)
            payload.mergeParams(extraParams);
        return payload;
    }

    /**
     * Method to assemble a payload for take profit and stop loss order request
     *
     * @param quantity:    quantity value in the order
     * @param key:         key for value (stopPrice or trailingDelta)
     * @param value:       level indicator value
     * @param extraParams: extraParams of the request
     * @return payload request as {@link Params}
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official {@code "Binance"}'s documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade">
     * Test New Order (TRADE)</a>
     **/
    public static Params getLevelPayload(double quantity, String key, double value, Params extraParams) {
        Params payload = new Params();
        payload.addParam("quantity", sNotationParse(8, quantity));
        payload.addParam(key, value);
        if (extraParams != null)
            payload.mergeParams(extraParams);
        return payload;
    }

    /**
     * Method to assemble a payload for take profit limit and stop loss limit order request
     *
     * @param timeInForce: time in force for the order
     * @param quantity:    quantity value in the order
     * @param price:       price value in the order
     * @param key:         key for value (stopPrice or trailingDelta)
     * @param value:       level indicator value
     * @param extraParams: extraParams of the request
     * @return payload request as {@link Params}
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official {@code "Binance"}'s documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade">
     * Test New Order (TRADE)</a>
     **/
    public static Params getLevelLimitPayload(TimeInForce timeInForce, double quantity, double price, String key, double value,
                                              Params extraParams) {
        Params payload = getLimitPayload(timeInForce, quantity, price, extraParams);
        payload.addParam(key, value);
        return payload;
    }

    /**
     * Method to assemble a payload for limit maker order request
     *
     * @param quantity:    quantity value in the order
     * @param price:       price value in the order
     * @param extraParams: extraParams of the request
     * @return payload request as {@link Params}
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official {@code "Binance"}'s documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade">
     * Test New Order (TRADE)</a>
     **/
    public static Params getLimitMakerPayload(double quantity, double price, Params extraParams) {
        Params payload = new Params();
        payload.addParam("quantity", sNotationParse(8, quantity));
        payload.addParam("price", price);
        if (extraParams != null)
            payload.mergeParams(extraParams);
        return payload;
    }

    /**
     * Method to get {@link #symbol} instance <br>
     * No-any params required
     *
     * @return {@link #symbol} instance as {@link String}
     **/
    public String getSymbol() {
        return symbol;
    }

    /**
     * Method to get {@link #orderId} instance <br>
     * No-any params required
     *
     * @return {@link #orderId} instance as long
     **/
    public long getOrderId() {
        return orderId;
    }

    /**
     * Method to get {@link #clientOrderId} instance <br>
     * No-any params required
     *
     * @return {@link #clientOrderId} instance as {@link String}
     **/
    public String getClientOrderId() {
        return clientOrderId;
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

    /**
     * {@code Side} list of available sides for an order
     **/
    public enum Side {

        /**
         * {@code "BUY"} side
         **/
        BUY,

        /**
         * {@code "SELL"} side
         **/
        SELL

    }

    /**
     * {@code OrderType} list of available types for an order
     **/
    public enum OrderType {

        /**
         * {@code "LIMIT"} type
         **/
        LIMIT,

        /**
         * {@code "MARKET"} type
         **/
        MARKET,

        /**
         * {@code "STOP_LOSS"} type
         **/
        STOP_LOSS,

        /**
         * {@code "STOP_LOSS_LIMIT"} type
         **/
        STOP_LOSS_LIMIT,

        /**
         * {@code "TAKE_PROFIT"} type
         **/
        TAKE_PROFIT,

        /**
         * {@code "TAKE_PROFIT_LIMIT"} type
         **/
        TAKE_PROFIT_LIMIT,

        /**
         * {@code "LIMIT_MAKER"} type
         **/
        LIMIT_MAKER

    }

    /**
     * {@code TimeInForce} list of available time in force for an order
     **/
    public enum TimeInForce {

        /**
         * {@code "GTC"} time in force -> {@code "Good Till Canceled"}
         **/
        GTC,

        /**
         * {@code "IOC"} time in force -> {@code "Immediate Or Cancel"}
         **/
        IOC,

        /**
         * {@code "FOK"} time in force -> {@code "Fill Or Kill"}
         **/
        FOK

    }

    /**
     * {@code OrderResponseType} list of available response type for an order
     **/
    public enum OrderResponseType {

        /**
         * {@code "ACK"} response type
         **/
        ACK,

        /**
         * {@code "RESULT"} response type
         **/
        RESULT,

        /**
         * {@code "FULL"} response type
         **/
        FULL

    }

    /**
     * {@code Status} list of available statuses for an order
     **/
    public enum Status {

        /**
         * {@code "CONFIRMED"} status
         **/
        CONFIRMED,

        /**
         * {@code "PENDING"} status
         **/
        PENDING,

        /**
         * {@code "FAILED"} status
         **/
        FAILED,

        /**
         * {@code "NEW"} status
         **/
        NEW,

        /**
         * {@code "PARTIALLY_FILLED"} status
         **/
        PARTIALLY_FILLED,

        /**
         * {@code "FILLED"} status
         **/
        FILLED,

        /**
         * {@code "CANCELED"} status
         **/
        CANCELED,

        /**
         * {@code "PENDING_CANCELED"} status
         **/
        PENDING_CANCELED,

        /**
         * {@code "REJECTED"} status
         **/
        REJECTED,

        /**
         * {@code "EXPIRED"} status
         **/
        EXPIRED,

        /**
         * {@code "EXECUTING"} status
         **/
        EXECUTING,

        /**
         * {@code "ALL_DONE"} status
         **/
        ALL_DONE,

        /**
         * {@code "REJECT"} status
         **/
        REJECT,

        /**
         * {@code "EXPIRED_IN_MATCH"} status
         **/
        EXPIRED_IN_MATCH

    }

    /**
     * {@code CancelRestriction} list of available cancel restrictions for an order
     **/
    public enum CancelRestriction {

        /**
         * {@code "ONLY_NEW"} cancel will succeed if the order status is {@link Status#NEW}
         **/
        ONLY_NEW,

        /**
         * {@code "ONLY_PARTIALLY_FILLED"} cancel will succeed if order status is {@link Status#PARTIALLY_FILLED}
         **/
        ONLY_PARTIALLY_FILLED

    }

}
