package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Cancel;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common.Order;
import org.json.JSONObject;

public class CancelMarginOrder extends Order {

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

    public CancelMarginOrder(String symbol, double orderId, String clientOrderId, String origClientOrderId, double price,
                             double origQty, double executedQty, double cummulativeQuoteQty, String status, String timeInForce,
                             String type, String side, JSONObject jsonObject) {
        super(symbol, orderId, clientOrderId);
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

    public JSONObject getJsonObject() {
        return jsonObject;
    }

}
