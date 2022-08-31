package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Details;

import com.tecknobit.apimanager.Tools.Formatters.JsonHelper;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common.Order;
import org.json.JSONObject;

/**
 *  The {@code DetailMarginOrder} class is useful to format Binance Margin Cancel Order request
 *  @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data">
 *      https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class DetailMarginOrder extends Order {

    /**
     * {@code isIsolated} is instance that memorizes if is isolated
     * **/
    private final boolean isIsolated;

    /**
     * {@code origClientOrderId} is instance that memorizes origin client order id
     * **/
    private final String origClientOrderId;

    /**
     * {@code price} is instance that memorizes price in the order
     * **/
    private final double price;

    /**
     * {@code origQty} is instance that memorizes origin quantity in the order
     * **/
    private final double origQty;

    /**
     * {@code executedQty} is instance that memorizes executed quantity in the order
     * **/
    private final double executedQty;

    /**
     * {@code cummulativeQuoteQty} is instance that memorizes cummulative quote quantity in the order
     * **/
    private final double cummulativeQuoteQty;

    /**
     * {@code status} is instance that memorizes status of the order
     * **/
    private final String status;

    /**
     * {@code timeInForce} is instance that memorizes time in force of the order
     * **/
    private final String timeInForce;

    /**
     * {@code type} is instance that memorizes type of the order
     * **/
    private final String type;

    /**
     * {@code side} is instance that memorizes side of the order
     * **/
    private final String side;

    /**
     * {@code jsonHelper} is instance that memorizes {@link JsonHelper} tool
     * **/
    private final JsonHelper jsonHelper;

    /** Constructor to init {@link DetailMarginOrder} object
     * @param symbol: symbol used in the order
     * @param orderId: order identifier
     * @param clientOrderId: client order identifier
     * @param isIsolated: is isolated
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
    public DetailMarginOrder(String symbol, double orderId, String clientOrderId, boolean isIsolated, String origClientOrderId,
                             double price, double origQty, double executedQty, double cummulativeQuoteQty, String status,
                             String timeInForce, String type, String side, JSONObject jsonOrder) {
        super(symbol, orderId, clientOrderId);
        this.isIsolated = isIsolated;
        this.origClientOrderId = origClientOrderId;
        this.price = price;
        this.origQty = origQty;
        this.executedQty = executedQty;
        this.cummulativeQuoteQty = cummulativeQuoteQty;
        this.status = status;
        this.timeInForce = timeInForce;
        this.type = type;
        this.side = side;
        jsonHelper = new JsonHelper(jsonOrder);
    }

    public boolean isIsolated() {
        return isIsolated;
    }

    public String getOrigClientOrderId() {
        return origClientOrderId;
    }

    public double getPrice() {
        return price;
    }

    public double getOrigQty() {
        return origQty;
    }

    public double getExecutedQty() {
        return executedQty;
    }

    public double getCummulativeQuoteQty() {
        return cummulativeQuoteQty;
    }

    public String getStatus() {
        return status;
    }

    public String getTimeInForce() {
        return timeInForce;
    }

    public String getType() {
        return type;
    }

    public String getSide() {
        return side;
    }

    /** Method to get stopPrice <br>
     * Any params required
     * @return stopPrice as double, if is a null field will return -1
     * **/
    public double getStopPrice(){
        return jsonHelper.getDouble("stopPrice");
    }

    /** Method to get icebergQty <br>
     * Any params required
     * @return icebergQty as double, if is a null field will return -1
     * **/
    public double getIcebergQty(){
        return jsonHelper.getDouble("icebergQty");
    }

    /** Method to assemble a DetailMarginOrder
     * @param cancelMarginOrder: obtained from Binance's request
     * retrun DetailMarginOrder object
     * **/
    public static DetailMarginOrder assembleDetailMarginOrderObject(JSONObject cancelMarginOrder){
        return new DetailMarginOrder(cancelMarginOrder.getString("symbol"),
                cancelMarginOrder.getLong("orderId"),
                cancelMarginOrder.getString("clientOrderId"),
                cancelMarginOrder.getBoolean("isIsolated"),
                cancelMarginOrder.getString("origClientOrderId"),
                cancelMarginOrder.getDouble("price"),
                cancelMarginOrder.getDouble("origQty"),
                cancelMarginOrder.getDouble("executedQty"),
                cancelMarginOrder.getDouble("cummulativeQuoteQty"),
                cancelMarginOrder.getString("status"),
                cancelMarginOrder.getString("timeInForce"),
                cancelMarginOrder.getString("type"),
                cancelMarginOrder.getString("side"),
                cancelMarginOrder
        );
    }

    @Override
    public String toString() {
        return "DetailMarginOrder{" +
                "isIsolated=" + isIsolated +
                ", origClientOrderId='" + origClientOrderId + '\'' +
                ", price=" + price +
                ", origQty=" + origQty +
                ", executedQty=" + executedQty +
                ", cummulativeQuoteQty=" + cummulativeQuoteQty +
                ", status='" + status + '\'' +
                ", timeInForce='" + timeInForce + '\'' +
                ", type='" + type + '\'' +
                ", side='" + side + '\'' +
                ", symbol='" + symbol + '\'' +
                ", orderId=" + orderId +
                ", clientOrderId='" + clientOrderId + '\'' +
                '}';
    }

}
