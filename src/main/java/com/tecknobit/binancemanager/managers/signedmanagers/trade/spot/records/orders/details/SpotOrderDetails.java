package com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.details;

import com.tecknobit.binancemanager.managers.BinanceManager.BinanceResponse;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.common.Order;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.response.ACKSpotOrder;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.response.ResultSpotOrder;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.response.SpotOrder;
import org.json.JSONObject;

import java.util.Date;

/**
 * The {@code SpotOrderDetails} class is useful to format a {@code "Binance"}'s spot order details
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade">
 * Cancel Order (TRADE)</a>
 * @see BinanceResponse
 * @see Order
 * @see ACKSpotOrder
 * @see ResultSpotOrder
 * @see SpotOrder
 **/
public class SpotOrderDetails extends ResultSpotOrder implements BinanceResponse {

    /**
     * {@code origClientOrderId} is instance that memorizes origin client order identifier
     **/
    private final String origClientOrderId;

    /**
     * Constructor to init {@link SpotOrderDetails} object
     *
     * @param symbol:              symbol used in the order
     * @param orderId:             order identifier
     * @param orderListId:         order list identifier
     * @param clientOrderId:       client order identifier
     * @param origClientOrderId:   origin client order id
     * @param price:               price in the order
     * @param origQty:             origin quantity in the order
     * @param executedQty:         executed quantity in the order
     * @param cummulativeQuoteQty: cummulative quote quantity in the order
     * @param status:              status of the order
     * @param timeInForce:         time in force of the order
     * @param type:                type of the order
     * @param side:                side of the order
     **/
    public SpotOrderDetails(String symbol, long orderId, long orderListId, String clientOrderId, String origClientOrderId,
                            double price, double origQty, double executedQty, double cummulativeQuoteQty, Status status,
                            TimeInForce timeInForce, OrderType type, Side side) {
        super(symbol, orderId, orderListId, clientOrderId, -1, price, origQty, executedQty, cummulativeQuoteQty,
                status, timeInForce, type, side);
        this.origClientOrderId = origClientOrderId;
    }

    /**
     * Constructor to init {@link SpotOrderDetails} object
     *
     * @param detailSpotOrder: spot order details as {@link JSONObject}
     **/
    public SpotOrderDetails(JSONObject detailSpotOrder) {
        super(detailSpotOrder);
        origClientOrderId = hOrder.getString("origClientOrderId");
    }

    /**
     * @return ever same value -1
     * @apiNote this method in {@link SpotOrderDetails} class will return ever -1
     **/
    @Override
    public long getTransactTime() {
        return -1;
    }

    /**
     * @return ever same value null
     * @apiNote this method in {@link SpotOrderDetails} class will return ever null
     **/
    @Override
    public Date getTransactDate() {
        return null;
    }

    /**
     * Method to get stopPrice <br>
     * Any params required
     *
     * @return stopPrice as double, if is a null field will return -1
     **/
    public double getStopPrice() {
        return hOrder.getDouble("stopPrice", -1);
    }

    /** Method to get icebergQty <br>
     * Any params required
     * @return icebergQty as double, if is a null field will return -1
     * **/
    public double getIcebergQty() {
        return hOrder.getDouble("icebergQty", -1);
    }

    /**
     * Method to get error code <br>
     * Any params required
     *
     * @return code of error as int
     * *
     * @implSpec if code error is not present in {@code "Binance"}'s response will be returned -1 as default
     **/
    @Override
    public int getCode() {
        if (hOrder != null)
            return hOrder.getInt("code", -1);
        return -1;
    }

    /**
     * Method to get error message <br>
     * Any params required
     *
     * @return message of error as {@link String}
     * *
     * @implSpec if message error is not present in {@code "Binance"}'s response will be returned null as default
     **/
    @Override
    public String getMessage() {
        if (hOrder != null)
            return hOrder.getString("msg", null);
        return null;
    }

}
