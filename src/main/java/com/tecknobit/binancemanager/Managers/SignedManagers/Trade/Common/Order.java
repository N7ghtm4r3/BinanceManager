package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common;

public class Order {

    private final String symbol;
    private final double orderId;
    private final String clientOrderId;

    public Order(String symbol, double orderId, String clientOrderId) {
        this.symbol = symbol;
        this.orderId = orderId;
        this.clientOrderId = clientOrderId;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getOrderId() {
        return orderId;
    }

    public String getClientOrderId() {
        return clientOrderId;
    }

}
