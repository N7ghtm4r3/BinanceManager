package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Response;

/**
 * The {@code MarginOrderStatus} class is useful to format ResultMarginOrder object of Binance's request Margin Account New Order
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
 *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade</a>
 * **/

public class MarginOrderStatus extends ResultMarginOrder {

    /**
     * {@code icebergQty} is instance that memorizes iceberg quantity
     * **/
    private final double icebergQty;

    /**
     * {@code isWorking} is instance that memorizes if is working
     * **/
    private final boolean isWorking;

    /**
     * {@code stopPrice} is instance that memorizes stop price value
     * **/
    private final double stopPrice;

    /**
     * {@code time} is instance that memorizes time value
     * **/
    private final long time;

    /** Constructor to init {@link MarginOrderStatus} object
     * @param symbol: symbol used in the order
     * @param orderId: order identifier
     * @param clientOrderId: client order identifier
     * @param updateTime: transaction time
     * @param isIsolated: is isolated
     * @param price: price in order
     * @param origQty: origin quantity in order
     * @param executedQty: executed quantity in order
     * @param cummulativeQuoteQty: cummulative quote quantity
     * @param status: status of the order
     * @param timeInForce: time in force of the order
     * @param type: type of the order
     * @param side: side of the order
     * @param icebergQty: iceberg quantity
     * @param isWorking: is working
     * @param stopPrice: stop price value
     * @param time: time value
     * **/
    public MarginOrderStatus(String symbol, double orderId, String clientOrderId, long updateTime, boolean isIsolated,
                             double price, double origQty, double executedQty, double cummulativeQuoteQty, String status,
                             String timeInForce, String type, String side, double icebergQty, boolean isWorking,
                             double stopPrice, long time) {
        super(symbol, orderId, clientOrderId, updateTime, isIsolated, price, origQty, executedQty, cummulativeQuoteQty,
                status, timeInForce, type, side);
        this.icebergQty = icebergQty;
        this.isWorking = isWorking;
        this.stopPrice = stopPrice;
        this.time = time;
    }

    public double getIcebergQty() {
        return icebergQty;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public double getStopPrice() {
        return stopPrice;
    }

    public long getTime() {
        return time;
    }

    public long getUpdateTime(){
        return transactTime;
    }

}
