package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders;

public class ACKOrder {

    public static final String LIMIT = "LIMIT";
    public static final String MARKET = "MARKET";
    public static final String STOP_LOSS = "STOP_LOSS";
    public static final String STOP_LOSS_LIMIT = "STOP_LOSS_LIMIT";
    public static final String TAKE_PROFIT = "TAKE_PROFIT";
    public static final String TAKE_PROFIT_LIMIT = "TAKE_PROFIT_LIMIT";
    public static final String LIMIT_MAKER = "LIMIT_MAKER";
    public static final String TIME_IN_FORCE_GTC = "GTC";
    public static final String TIME_IN_FORCE_IOC = "IOC";
    public static final String TIME_IN_FORCE_FOK = "FOK";
    public static final String NEW_ORDER_RESP_TYPE_ACK = "ACK";
    public static final String NEW_ORDER_RESP_TYPE_RESULT = "RESULT";
    public static final String NEW_ORDER_RESP_TYPE_FULL = "FULL";
    private final String symbol;
    private final long orderId;
    private final long orderListId;
    private final String clientOrderId;
    private final long transactTime;

    public ACKOrder(String symbol, long orderId, long orderListId, String clientOrderId, long transactTime) {
        this.symbol = symbol;
        this.orderId = orderId;
        this.orderListId = orderListId;
        this.clientOrderId = clientOrderId;
        this.transactTime = transactTime;
    }

    public String getSymbol() {
        return symbol;
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

    public long getTransactTime() {
        return transactTime;
    }

}
