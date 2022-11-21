package com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.response;

import com.tecknobit.binancemanager.managers.BinanceManager.Params;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.common.Order;
import org.json.JSONObject;

import static com.tecknobit.apimanager.formatters.ScientificNotationParser.sNotationParse;

/**
 * The {@code SpotOrder} class is useful to manage all SpotOrder {@code "Binance"} request
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#spot-account-trade">
 * https://binance-docs.github.io/apidocs/spot/en/#spot-account-trade</a>
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

    public long getOrderListId() {
        return orderListId;
    }

    /** Method to assemble a payload for limit order request
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param extraParams: extraParams of the request
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official {@code "Binance"}'s documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade</a>
     * @return payload request as {@link Params}
     * **/
    public static Params getLimitPayload(String timeInForce, double quantity, double price, Params extraParams){
        Params payload = new Params();
        payload.addParam("timeInForce", timeInForce);
        payload.addParam("quantity", sNotationParse(8, quantity));
        payload.addParam("price", price);
        if(extraParams != null)
            payload.mergeParams(extraParams);
        return payload;
    }

    /** Method to assemble a payload for market order request
     * @param keyQty: key for qty value (quantity or quoteOrderQty)
     * @param qty: quantity value in the order
     * @param extraParams: extraParams of the request
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official {@code "Binance"}'s documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade</a>
     * @return payload request as {@link Params}
     * **/
    public static Params getMarketPayload(String keyQty, double qty, Params extraParams){
        Params payload = new Params();
        payload.addParam(keyQty, sNotationParse(8, qty));
        if(extraParams != null)
            payload.mergeParams(extraParams);
        return payload;
    }

    /** Method to assemble a payload for take profit and stop loss order request
     * @param quantity: quantity value in the order
     * @param key: key for value (stopPrice or trailingDelta)
     * @param value: level indicator value
     * @param extraParams: extraParams of the request
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official {@code "Binance"}'s documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade</a>
     * @return payload request as {@link Params}
     * **/
    public static Params getLevelPayload(double quantity, String key, double value, Params extraParams){
        Params payload = new Params();
        payload.addParam("quantity", sNotationParse(8, quantity));
        payload.addParam(key, value);
        if(extraParams != null)
            payload.mergeParams(extraParams);
        return payload;
    }

    /** Method to assemble a payload for take profit limit and stop loss limit order request
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param key: key for value (stopPrice or trailingDelta)
     * @param value: level indicator value
     * @param extraParams: extraParams of the request
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official {@code "Binance"}'s documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade</a>
     * @return payload request as {@link Params}
     * **/
    public static Params getLevelLimitPayload(String timeInForce, double quantity, double price, String key, double value,
                                              Params extraParams){
        Params payload = getLimitPayload(timeInForce, quantity, price, extraParams);
        payload.addParam(key, value);
        return payload;
    }

    /** Method to assemble a payload for limit maker order request
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param extraParams: extraParams of the request
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official {@code "Binance"}'s documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade</a>
     * @return payload request as {@link Params}
     * **/
    public static Params getLimitMakerPayload(double quantity, double price, Params extraParams){
        Params payload = new Params();
        payload.addParam("quantity", sNotationParse(8, quantity));
        payload.addParam("price", price);
        if(extraParams != null)
            payload.mergeParams(extraParams);
        return payload;
    }

    @Override
    public String toString() {
        return "SpotOrder{" +
                "orderListId=" + orderListId +
                ", symbol='" + symbol + '\'' +
                ", orderId=" + orderId +
                ", clientOrderId='" + clientOrderId + '\'' +
                '}';
    }

}
