package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.orders.response;

import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code MarginOrderStatus} class is useful to format ResultMarginOrder object of {@code "Binance"}'s request Margin Account New Order
 *
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
 * https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade</a>
 **/

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

    /**
     * Constructor to init {@link MarginOrderStatus} object
     *
     * @param resultMarginOrder: result margin order details as {@link JSONObject}
     **/
    public MarginOrderStatus(JSONObject resultMarginOrder) {
        super(resultMarginOrder);
        icebergQty = resultMarginOrder.getDouble("icebergQty");
        isWorking = resultMarginOrder.getBoolean("isWorking");
        stopPrice = resultMarginOrder.getDouble("stopPrice");
        time = resultMarginOrder.getLong("time");
    }

    public double getIcebergQty() {
        return icebergQty;
    }

    /**
     * Method to get {@link #icebergQty} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #icebergQty} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getIcebergQty(int decimals) {
        return roundValue(icebergQty, decimals);
    }

    public boolean isWorking() {
        return isWorking;
    }

    public double getStopPrice() {
        return stopPrice;
    }

    /**
     * Method to get {@link #stopPrice} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #stopPrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getStopPrice(int decimals) {
        return roundValue(stopPrice, decimals);
    }

    public long getTime() {
        return time;
    }

    public long getUpdateTime() {
        return transactTime;
    }

    @Override
    public String toString() {
        return "MarginOrderStatus{" +
                "icebergQty=" + icebergQty +
                ", isWorking=" + isWorking +
                ", stopPrice=" + stopPrice +
                ", time=" + time +
                ", price=" + price +
                ", origQty=" + origQty +
                ", executedQty=" + executedQty +
                ", cummulativeQuoteQty=" + cummulativeQuoteQty +
                ", status='" + status + '\'' +
                ", timeInForce='" + timeInForce + '\'' +
                ", type='" + type + '\'' +
                ", side='" + side + '\'' +
                ", isIsolated=" + isIsolated +
                ", transactTime=" + transactTime +
                ", symbol='" + symbol + '\'' +
                ", orderId=" + orderId +
                ", clientOrderId='" + clientOrderId + '\'' +
                '}';
    }

}
