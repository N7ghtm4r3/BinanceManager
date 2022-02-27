package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Cancel;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Order;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *  The {@code CancelOrder} class is useful to format all CancelOrder Binance request in CancelOrder format
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class CancelOrder extends Order {

    private final String origClientOrderId;
    private final double price;
    private final double origQty;
    private final double executedQty;
    private final double cummulativeQuoteQty;
    private final String status;
    private final String timeInForce;
    private final String type;
    private final String side;
    private final JSONObject jsonObject;

    public CancelOrder(String symbol, long orderId, long orderListId, String clientOrderId, String origClientOrderId,
                       double price, double origQty, double executedQty, double cummulativeQuoteQty, String status,
                       String timeInForce, String type, String side, JSONObject jsonObject) {
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
        this.jsonObject = jsonObject;
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
     * any params requirde
     * return stopPrice as double, if is a null field will return -1
     * **/
    public double getStopPrice(){
        return getDoubleValue("stopPrice");
    }

    /** Method to get icebergQty
     * any params requirde
     * return icebergQty as double, if is a null field will return -1
     * **/
    public double getIcebergQty(){
        return getDoubleValue("icebergQty");
    }

    /** Method to assemble a CancelOrder object
     * @param #key: field to fetch a double value in {@link #jsonObject}
     * return an CancelOrder object with response data
     * **/
    private double getDoubleValue(String key){
        try {
            return jsonObject.getDouble(key);
        }catch (JSONException e){
            return -1;
        }
    }

    /** Method to assemble a CancelOrder object
     * @param #response: obtained from Binance's request
     * return an CancelOrder object with response data
     * **/
    public static CancelOrder assembleCancelOrderObject(JSONObject response){
        return new CancelOrder(response.getString("symbol"),
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

}
