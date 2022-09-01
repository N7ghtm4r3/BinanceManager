package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Details;

import com.tecknobit.binancemanager.Managers.BinanceManager;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Response.ResultSpotOrder;
import org.json.JSONObject;

/**
 * The {@code DetailSpotOrder} class is useful to format all DetailSpotOrder Binance request in DetailSpotOrder format
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade">
 * https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade</a>
 **/

public class DetailSpotOrder extends ResultSpotOrder implements BinanceManager.BinanceResponse {

    /**
     * {@code origClientOrderId} is instance that memorizes origin client order identifier
     **/
    private final String origClientOrderId;

    /** Constructor to init {@link DetailSpotOrder} object
     * @param symbol: symbol used in the order
     * @param orderId: order identifier
     * @param orderListId: order list identifier
     * @param clientOrderId: client order identifier
     * @param origClientOrderId: origin client order id
     * @param price: price in the order
     * @param origQty: origin quantity in the order
     * @param executedQty: executed quantity in the order
     * @param cummulativeQuoteQty: cummulative quote quantity in the order
     * @param status: status of the order
     * @param timeInForce: time in force of the order
     * @param type: type of the order
     * @param side: side of the order
     * @param jsonOrder: order details as {@link JSONObject}
     * **/
    public DetailSpotOrder(String symbol, long orderId, long orderListId, String clientOrderId, String origClientOrderId,
                           double price, double origQty, double executedQty, double cummulativeQuoteQty, String status,
                           String timeInForce, String type, String side, JSONObject jsonOrder) {
        super(symbol, orderId, orderListId, clientOrderId, -1, price, origQty, executedQty, cummulativeQuoteQty,
                status, timeInForce, type, side);
        this.origClientOrderId = origClientOrderId;
    }

    /**
     * Constructor to init {@link DetailSpotOrder} object
     *
     * @param detailSpotOrder: spot order details as {@link JSONObject}
     **/
    public DetailSpotOrder(JSONObject detailSpotOrder) {
        super(detailSpotOrder);
        origClientOrderId = hOrder.getString("origClientOrderId");
    }

    /**
     * @return ever same value -1
     * @apiNote this method in {@link DetailSpotOrder} class will return ever -1
     **/
    @Override
    public long getTransactTime() {
        return super.getTransactTime();
    }

    /**
     * Method to get stopPrice <br>
     * Any params required
     *
     * @return stopPrice as double, if is a null field will return -1
     **/
    public double getStopPrice() {
        return hOrder.getDouble("stopPrice");
    }

    /** Method to get icebergQty <br>
     * Any params required
     * @return icebergQty as double, if is a null field will return -1
     * **/
    public double getIcebergQty() {
        return hOrder.getDouble("icebergQty");
    }

    /**
     * Method to get error code <br>
     * Any params required
     *
     * @return code of error as int
     * *
     * @implSpec if code error is not present in Binance's response will be returned -1 as default
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
     * @implSpec if message error is not present in Binance's response will be returned null as default
     **/
    @Override
    public String getMessage() {
        if (hOrder != null)
            return hOrder.getString("msg", null);
        return null;
    }

    @Override
    public String toString() {
        return "DetailSpotOrder{" +
                "origClientOrderId='" + origClientOrderId + '\'' +
                ", price=" + price +
                ", origQty=" + origQty +
                ", executedQty=" + executedQty +
                ", cummulativeQuoteQty=" + cummulativeQuoteQty +
                ", status='" + status + '\'' +
                ", timeInForce='" + timeInForce + '\'' +
                ", type='" + type + '\'' +
                ", side='" + side + '\'' +
                ", orderListId=" + orderListId +
                ", symbol='" + symbol + '\'' +
                ", orderId=" + orderId +
                ", clientOrderId='" + clientOrderId + '\'' +
                '}';
    }

}
