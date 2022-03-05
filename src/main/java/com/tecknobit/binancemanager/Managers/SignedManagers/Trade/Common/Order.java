package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common;

/**
 * The {@code Order} class is useful to manage and format all Binance Order request
 * @implNote used by BinanceSpotManager, BinanceMarginManager
 * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#introduction
 * **/

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
