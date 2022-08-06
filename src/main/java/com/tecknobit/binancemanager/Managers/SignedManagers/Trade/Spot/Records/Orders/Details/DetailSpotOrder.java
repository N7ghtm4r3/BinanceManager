package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Details;

import com.tecknobit.apimanager.Tools.Formatters.JsonHelper;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.SpotOrder;
import org.json.JSONObject;

/**
 *  The {@code DetailSpotOrder} class is useful to format all DetailSpotOrder Binance request in DetailSpotOrder format
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade">
 *      https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class DetailSpotOrder extends SpotOrder {

    /**
     * {@code origClientOrderId} is instance that memorizes origin client order identifier
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
     * @param jsonOrder: order details in JSON format
     * **/
    public DetailSpotOrder(String symbol, long orderId, long orderListId, String clientOrderId, String origClientOrderId,
                           double price, double origQty, double executedQty, double cummulativeQuoteQty, String status,
                           String timeInForce, String type, String side, JSONObject jsonOrder) {
        super(symbol, orderId, orderListId, clientOrderId);
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

    /** Method to get stopPrice
     * any params required
     * @return stopPrice as double, if is a null field will return -1
     * **/
    public double getStopPrice(){
        return jsonHelper.getDouble("stopPrice");
    }

    /** Method to get icebergQty
     * any params required
     * @return icebergQty as double, if is a null field will return -1
     * **/
    public double getIcebergQty(){
        return jsonHelper.getDouble("icebergQty");
    }

    /** Method to assemble a DetailSpotOrder object
     * @param response: obtained from Binance's request
     * @return an DetailSpotOrder object with response data
     * **/
    public static DetailSpotOrder assembleDetailSpotOrderObject(JSONObject response){
        return new DetailSpotOrder(response.getString("symbol"),
                response.getLong("orderId"),
                response.getLong("orderListId"),
                response.getString("clientOrderId"),
                response.getString("origClientOrderId"),
                response.getDouble("price"),
                response.getDouble("origQty"),
                response.getDouble("executedQty"),
                response.getDouble("cummulativeQuoteQty"),
                response.getString("status"),
                response.getString("timeInForce"),
                response.getString("type"),
                response.getString("side"),
                response);
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
                ", jsonHelper=" + jsonHelper +
                ", orderListId=" + orderListId +
                ", symbol='" + symbol + '\'' +
                ", orderId=" + orderId +
                ", clientOrderId='" + clientOrderId + '\'' +
                '}';
    }

}
