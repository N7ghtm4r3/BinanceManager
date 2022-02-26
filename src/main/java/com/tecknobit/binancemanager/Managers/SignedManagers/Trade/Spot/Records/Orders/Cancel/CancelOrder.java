package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Cancel;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Order;
import org.json.JSONException;
import org.json.JSONObject;

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

    /** indicate default value return**/
    public double getStopPrice(){
        return getDoubleValue("stopPrice");
    }

    /** indicate default value return**/
    public double getIcebergQty(){
        return getDoubleValue("icebergQty");
    }

    private double getDoubleValue(String key){
        try {
            return jsonObject.getDouble(key);
        }catch (JSONException e){
            return -1;
        }
    }

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
