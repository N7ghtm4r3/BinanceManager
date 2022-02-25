package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Cancel;

public class CancelOrder {

    private final String symbol;
    private final String origClientOrderId;
    private final long orderId;
    private final long orderListId;
    private final String clientOrderId;
    private final double price;
    private final double origQty;
    private final double executedQty;
    private final double cummulativeQuoteQty;
    private final String status;
    private final String timeInForce;
    private final String type;
    private final String side;

    public CancelOrder(String symbol, String origClientOrderId, long orderId, long orderListId, String clientOrderId,
                       double price, double origQty, double executedQty, double cummulativeQuoteQty, String status,
                       String timeInForce, String type, String side) {
        this.symbol = symbol;
        this.origClientOrderId = origClientOrderId;
        this.orderId = orderId;
        this.orderListId = orderListId;
        this.clientOrderId = clientOrderId;
        this.price = price;
        this.origQty = origQty;
        this.executedQty = executedQty;
        this.cummulativeQuoteQty = cummulativeQuoteQty;
        this.status = status;
        this.timeInForce = timeInForce;
        this.type = type;
        this.side = side;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getOrigClientOrderId() {
        return origClientOrderId;
    }

    public long getOrderId() {
        return orderId;
    }

    public long getOrderListId() {
        return orderListId;
    }

    public String getClientOrderId() {
        return clientOrderId;
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

}
