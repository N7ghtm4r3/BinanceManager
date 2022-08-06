package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Response;

/**
 *  The {@code SpotOrderStatus} class is useful to format an SpotOrderStatus object
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data">
 *      https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class SpotOrderStatus extends ResultSpotOrder {

    /**
     * {@code stopPrice} is instance that memorizes stop price value
     * **/
    private final double stopPrice;

    /**
     * {@code icebergQty} is instance that memorizes iceberg quantity value
     * **/
    private final double icebergQty;

    /**
     * {@code time} is instance that memorizes time value
     * **/
    private final long time;

    /**
     * {@code isWorking} is instance that memorizes if is working
     * **/
    private final boolean isWorking;

    /**
     * {@code origQuoteOrderQty} is instance that memorizes origin quote quantity value
     * **/
    private final double origQuoteOrderQty;

    /** Constructor to init {@link SpotOrderStatus} object
     * @param symbol: symbol used in the order
     * @param orderId: order identifier
     * @param clientOrderId: client order identifier
     * @param orderListId: list order identifier
     * @param updateTime: update time
     * @param price: price in order
     * @param origQty: origin quantity in order
     * @param executedQty: executed quantity in order
     * @param cummulativeQuoteQty: cummulative quote quantity
     * @param status: status of the order
     * @param timeInForce: time in force of the order
     * @param type: type of the order
     * @param side: side of the order
     * **/
    public SpotOrderStatus(String symbol, long orderId, long orderListId, String clientOrderId, double price, double origQty,
                           double executedQty, double cummulativeQuoteQty, String status, String timeInForce, String type,
                           String side, double stopPrice, double icebergQty, long time, long updateTime, boolean isWorking,
                           double origQuoteOrderQty) {
        super(symbol, orderId, orderListId, clientOrderId, updateTime, price, origQty, executedQty, cummulativeQuoteQty,
                status, timeInForce, type, side);
        this.stopPrice = stopPrice;
        this.icebergQty = icebergQty;
        this.time = time;
        this.isWorking = isWorking;
        this.origQuoteOrderQty = origQuoteOrderQty;
    }

    public double getStopPrice() {
        return stopPrice;
    }

    public double getIcebergQty() {
        return icebergQty;
    }

    public long getTime() {
        return time;
    }

    public long getUpdateTime() {
        return transactTime;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public double getOrigQuoteOrderQty() {
        return origQuoteOrderQty;
    }

    @Override
    public String toString() {
        return "SpotOrderStatus{" +
                "stopPrice=" + stopPrice +
                ", icebergQty=" + icebergQty +
                ", time=" + time +
                ", isWorking=" + isWorking +
                ", origQuoteOrderQty=" + origQuoteOrderQty +
                ", price=" + price +
                ", origQty=" + origQty +
                ", executedQty=" + executedQty +
                ", cummulativeQuoteQty=" + cummulativeQuoteQty +
                ", status='" + status + '\'' +
                ", timeInForce='" + timeInForce + '\'' +
                ", type='" + type + '\'' +
                ", side='" + side + '\'' +
                ", transactTime=" + transactTime +
                ", orderListId=" + orderListId +
                ", symbol='" + symbol + '\'' +
                ", orderId=" + orderId +
                ", clientOrderId='" + clientOrderId + '\'' +
                '}';
    }

}
