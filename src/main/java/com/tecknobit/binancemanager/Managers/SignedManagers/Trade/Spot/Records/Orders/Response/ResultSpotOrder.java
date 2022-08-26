package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Response;

import org.json.JSONObject;

import static com.tecknobit.apimanager.Tools.Trading.TradingTools.roundValue;

/**
 * The {@code FullOrder} class is useful to format all SpotOrder Binance request in ResultOrder format
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
 * https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
 **/

public class ResultSpotOrder extends ACKSpotOrder {

    /**
     * {@code price} is instance that memorizes price in the order
     * **/
    protected final double price;

    /**
     * {@code origQty} is instance that memorizes origin quantity in the order
     * **/
    protected final double origQty;

    /**
     * {@code executedQty} is instance that memorizes executed quantity in the order
     * **/
    protected final double executedQty;

    /**
     * {@code cummulativeQuoteQty} is instance that memorizes cummulative quantity in the order
     * **/
    protected final double cummulativeQuoteQty;

    /**
     * {@code status} is instance that memorizes status of the order
     * **/
    protected final String status;

    /**
     * {@code timeInForce} is instance that memorizes time in force of the order
     * **/
    protected final String timeInForce;

    /**
     * {@code type} is instance that memorizes type of the order
     * **/
    protected final String type;

    /**
     * {@code side} is instance that memorizes side of the order
     * **/
    protected final String side;

    /** Constructor to init {@link ResultSpotOrder} object
     * @param symbol: symbol used in the order
     * @param orderId: order identifier
     * @param orderListId: list order identifier
     * @param clientOrderId: client order identifier
     * @param transactTime: transaction time
     * @param price: price in order
     * @param origQty: origin quantity in order
     * @param executedQty: executed quantity in order
     * @param cummulativeQuoteQty: cummulative quote quantity
     * @param status: status of the order
     * @param timeInForce: time in force of the order
     * @param type: type of the order
     * @param side: side of the order
     * **/
    public ResultSpotOrder(String symbol, long orderId, long orderListId, String clientOrderId,
                           long transactTime, double price, double origQty, double executedQty, double cummulativeQuoteQty,
                           String status, String timeInForce, String type, String side) {
        super(symbol, orderId, orderListId, clientOrderId, transactTime);
        this.price = price;
        this.origQty = origQty;
        this.executedQty = executedQty;
        this.cummulativeQuoteQty = cummulativeQuoteQty;
        this.status = status;
        this.timeInForce = timeInForce;
        this.type = type;
        this.side = side;
    }

    /**
     * Constructor to init {@link ResultSpotOrder} object
     *
     * @param resultOrder: result order details as {@link JSONObject}
     **/
    public ResultSpotOrder(JSONObject resultOrder) {
        super(resultOrder);
        price = hOrder.getDouble("price");
        origQty = hOrder.getDouble("origQty");
        executedQty = hOrder.getDouble("executedQty");
        cummulativeQuoteQty = hOrder.getDouble("cummulativeQuoteQty");
        status = hOrder.getString("status");
        timeInForce = hOrder.getString("timeInForce");
        type = hOrder.getString("type");
        side = hOrder.getString("side");
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
        return roundValue(executedQty, decimals);
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

    @Override
    public String toString() {
        return "ResultSpotOrder{" +
                "price=" + price +
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
