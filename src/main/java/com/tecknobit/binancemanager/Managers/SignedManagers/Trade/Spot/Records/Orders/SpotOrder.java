package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders;

import com.tecknobit.binancemanager.Managers.BinanceManager;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common.Order;

import java.util.HashMap;

import static com.tecknobit.apimanager.Tools.Formatters.ScientificNotationParser.sNotationParse;

/**
 *  The {@code SpotOrder} class is useful to manage all SpotOrder Binance request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#spot-account-trade">
 *      https://binance-docs.github.io/apidocs/spot/en/#spot-account-trade</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class SpotOrder extends Order {

    /**
     * {@code orderListId} is instance that memorizes order list identifier
     * **/
    protected final long orderListId;

    /** Constructor to init {@link SpotOrder} object
     * @param symbol: symbol used in the order
     * @param orderId: order identifier
     * @param clientOrderId: client order identifier
     * @param orderListId: list order identifier
     * **/
    public SpotOrder(String symbol, double orderId, long orderListId, String clientOrderId) {
        super(symbol, orderId, clientOrderId);
        this.orderListId = orderListId;
    }

    public long getOrderListId() {
        return orderListId;
    }

    /** Method to assemble a payload for limit order request
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param extraParams:  extraParams of the request
     * @implSpec (keys accepted are timeInForce,quantity,quoteOrderQty,price,newClientOrderId,stopPrice,icebergQty,
     * newOrderRespType,recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade</a>
     * @return payload request as {@link HashMap}
     * **/
    public static BinanceManager.Params getLimitPayload(String timeInForce, double quantity, double price,
                                                        BinanceManager.Params extraParams){
        BinanceManager.Params payload = new BinanceManager.Params();
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
     * @param extraParams:  extraParams of the request
     * @implSpec (keys accepted are timeInForce,quantity,quoteOrderQty,price,newClientOrderId,stopPrice,icebergQty,
     * newOrderRespType,recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade</a>
     * @return payload request as {@link HashMap}
     * **/
    public static BinanceManager.Params getMarketPayload(String keyQty, double qty, BinanceManager.Params extraParams){
        BinanceManager.Params payload = new BinanceManager.Params();
        payload.addParam(keyQty, sNotationParse(8, qty));
        if(extraParams != null)
            payload.mergeParams(extraParams);
        return payload;
    }

    /** Method to assemble a payload for take profit and stop loss order request
     * @param quantity: quantity value in the order
     * @param key: key for value (stopPrice or trailingDelta)
     * @param value: level indicator value
     * @param extraParams:  extraParams of the request
     * @implSpec (keys accepted are timeInForce,quantity,quoteOrderQty,price,newClientOrderId,stopPrice,icebergQty,
     * newOrderRespType,recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade</a>
     * @return payload request as {@link HashMap}
     * **/
    public static BinanceManager.Params getLevelPayload(double quantity, String key, double value,
                                                          BinanceManager.Params extraParams){
        BinanceManager.Params payload = new BinanceManager.Params();
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
     * @param extraParams:  extraParams of the request
     * @implSpec (keys accepted are timeInForce,quantity,quoteOrderQty,price,newClientOrderId,stopPrice,icebergQty,
     * newOrderRespType,recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade</a>
     * @return payload request as {@link HashMap}
     * **/
    public static BinanceManager.Params getLevelLimitPayload(String timeInForce, double quantity, double price,
                                                             String key, double value, BinanceManager.Params extraParams){
        BinanceManager.Params payload = getLimitPayload(timeInForce, quantity, price, extraParams);
        payload.addParam(key, value);
        return payload;
    }

    /** Method to assemble a payload for limit maker order request
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param extraParams:  extraParams of the request
     * @implSpec (keys accepted are timeInForce,quantity,quoteOrderQty,price,newClientOrderId,stopPrice,icebergQty,
     * newOrderRespType,recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade</a>
     * @return payload request as {@link HashMap}
     * **/
    public static BinanceManager.Params getLimitMakerPayload(double quantity, double price,
                                                               BinanceManager.Params extraParams){
        BinanceManager.Params payload = new BinanceManager.Params();
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
