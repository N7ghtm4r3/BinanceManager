package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common;

/**
 * The {@code Order} class is useful to manage and format all Binance Order request
 * @implNote used by BinanceSpotManager, BinanceMarginManager
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#introduction">https://binance-docs.github.io/apidocs/spot/en/#introduction</a>
 * **/

public class Order {

    /**Statuses for an order**/
    public static final String STATUS_CONFIRMED = "CONFIRMED";
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_FAILED = "FAILED";
    public static final String STATUS_NEW = "NEW";
    public static final String STATUS_PARTIALLY_FILLED = "PARTIALLY_FILLED";
    public static final String STATUS_FILLED = "FILLED";
    public static final String STATUS_CANCELED = "CANCELED";
    public static final String STATUS_PENDING_CANCELED = "PENDING_CANCELED";
    public static final String STATUS_REJECTED = "REJECTED";
    public static final String STATUS_EXPIRED = "EXPIRED";
    public static final String STATUS_EXECUTING = "EXECUTING";
    public static final String STATUS_ALL_DONE = "ALL_DONE";
    public static final String STATUS_REJECT = "REJECT";
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
