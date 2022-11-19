package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.orders.details;

import com.tecknobit.apimanager.formatters.JsonHelper;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.common.Order;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code DetailMarginOrder} class is useful to format Binance Margin Cancel Order request
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data</a>
 **/

public class DetailMarginOrder extends Order {

    /**
     * {@code isIsolated} is instance that memorizes if is isolated
     * **/
    private final boolean isIsolated;

    /**
     * {@code origClientOrderId} is instance that memorizes origin client order id
     * **/
    private final String origClientOrderId;

    /**
     * {@code price} is instance that memorizes price in the order
     * **/
    private final double price;

    /**
     * {@code origQty} is instance that memorizes origin quantity in the order
     * **/
    private final double origQty;

    /**
     * {@code executedQty} is instance that memorizes executed quantity in the order
     * **/
    private final double executedQty;

    /**
     * {@code cummulativeQuoteQty} is instance that memorizes cummulative quote quantity in the order
     * **/
    private final double cummulativeQuoteQty;

    /**
     * {@code status} is instance that memorizes status of the order
     * **/
    private final String status;

    /**
     * {@code timeInForce} is instance that memorizes time in force of the order
     * **/
    private final String timeInForce;

    /**
     * {@code type} is instance that memorizes type of the order
     * **/
    private final String type;

    /**
     * {@code side} is instance that memorizes side of the order
     * **/
    private final String side;

    /**
     * {@code jsonHelper} is instance that memorizes {@link JsonHelper} tool
     **/
    private final JsonHelper hMarginOrder;

    /** Constructor to init {@link DetailMarginOrder} object
     * @param symbol: symbol used in the order
     * @param orderId: order identifier
     * @param clientOrderId: client order identifier
     * @param isIsolated: is isolated
     * @param origClientOrderId: origin client order id
     * @param price: price in the order
     * @param origQty: origin quantity in the order
     * @param executedQty: executed quantity in the order
     * @param cummulativeQuoteQty: cummulative quote quantity in the order
     * @param status: status of the order
     * @param timeInForce: time in force of the order
     * @param type: type of the order
     * @param side: side of the order
     * **/
    public DetailMarginOrder(String symbol, double orderId, String clientOrderId, boolean isIsolated, String origClientOrderId,
                             double price, double origQty, double executedQty, double cummulativeQuoteQty,
                             String status, String timeInForce, String type, String side) {
        super(symbol, orderId, clientOrderId);
        this.isIsolated = isIsolated;
        this.origClientOrderId = origClientOrderId;
        this.price = price;
        this.origQty = origQty;
        this.executedQty = executedQty;
        this.cummulativeQuoteQty = cummulativeQuoteQty;
        this.status = status;
        this.timeInForce = timeInForce;
        this.type = type;
        this.side = side;
        hMarginOrder = null;
    }

    /**
     * Constructor to init {@link DetailMarginOrder} object
     *
     * @param marginOrder: margin order details as {@link JSONObject}
     **/
    public DetailMarginOrder(JSONObject marginOrder) {
        super(marginOrder);
        isIsolated = marginOrder.getBoolean("isIsolated");
        origClientOrderId = marginOrder.getString("origClientOrderId");
        price = marginOrder.getDouble("price");
        origQty = marginOrder.getDouble("origQty");
        executedQty = marginOrder.getDouble("executedQty");
        cummulativeQuoteQty = marginOrder.getDouble("cummulativeQuoteQty");
        status = marginOrder.getString("status");
        timeInForce = marginOrder.getString("timeInForce");
        type = marginOrder.getString("type");
        side = marginOrder.getString("side");
        hMarginOrder = new JsonHelper(marginOrder);
    }

    public boolean isIsolated() {
        return isIsolated;
    }

    public String getOrigClientOrderId() {
        return origClientOrderId;
    }

    public double getPrice() {
        return price;
    }

    /**
     * Method to get {@link #price} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #price} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getPrice(int decimals) {
        return roundValue(price, decimals);
    }

    public double getOrigQty() {
        return origQty;
    }

    /**
     * Method to get {@link #origQty} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #origQty} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getOrigQty(int decimals) {
        return roundValue(origQty, decimals);
    }

    public double getExecutedQty() {
        return executedQty;
    }

    /**
     * Method to get {@link #executedQty} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #executedQty} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getExecutedQty(int decimals) {
        return roundValue(origQty, decimals);
    }

    public double getCummulativeQuoteQty() {
        return cummulativeQuoteQty;
    }

    /**
     * Method to get {@link #cummulativeQuoteQty} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #cummulativeQuoteQty} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getCummulativeQuoteQty(int decimals) {
        return roundValue(cummulativeQuoteQty, decimals);
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

    /**
     * Method to get {@code stop price} value <br>
     * Any params required
     *
     * @return stopPrice as double, if is a null field will return -1
     **/
    public double getStopPrice() {
        if (hMarginOrder == null)
            return -1;
        return hMarginOrder.getDouble("stopPrice");
    }

    /**
     * Method to get {@code stop price} value <br>
     * Any params required
     *
     * @return stopPrice as double, if is a null field will return -1
     **/
    public double getStopPrice(int decimals) {
        return roundValue(getStopPrice(), decimals);
    }

    /**
     * Method to get {@code iceberg quantity} value <br>
     * Any params required
     *
     * @return icebergQty as double, if is a null field will return -1
     **/
    public double getIcebergQty() {
        if (hMarginOrder == null)
            return -1;
        return hMarginOrder.getDouble("icebergQty");
    }

    /**
     * Method to get {@code iceberg quantity} <br>
     * Any params required
     *
     * @return icebergQty as double, if is a null field will return -1
     **/
    public double getIcebergQty(int decimals) {
        return roundValue(getIcebergQty(), decimals);
    }

    @Override
    public String toString() {
        return "DetailMarginOrder{" +
                "isIsolated=" + isIsolated +
                ", origClientOrderId='" + origClientOrderId + '\'' +
                ", price=" + price +
                ", origQty=" + origQty +
                ", executedQty=" + executedQty +
                ", cummulativeQuoteQty=" + cummulativeQuoteQty +
                ", status='" + status + '\'' +
                ", timeInForce='" + timeInForce + '\'' +
                ", type='" + type + '\'' +
                ", side='" + side + '\'' +
                ", symbol='" + symbol + '\'' +
                ", orderId=" + orderId +
                ", clientOrderId='" + clientOrderId + '\'' +
                '}';
    }

}
