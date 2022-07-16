package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Details;

import com.tecknobit.apimanager.Tools.Readers.JsonHelper;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.SpotOrder;
import org.json.JSONObject;

/**
 *  The {@code DetailSpotOrder} class is useful to format all DetailSpotOrder Binance request in DetailSpotOrder format
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade">https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class DetailSpotOrder extends SpotOrder {

    private final String origClientOrderId;
    private final double price;
    private final double origQty;
    private final double executedQty;
    private final double cummulativeQuoteQty;
    private final String status;
    private final String timeInForce;
    private final String type;
    private final String side;
    private final JsonHelper jsonHelper;

    public DetailSpotOrder(String symbol, long orderId, long orderListId, String clientOrderId, String origClientOrderId,
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
        jsonHelper = new JsonHelper(jsonObject);
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

}
