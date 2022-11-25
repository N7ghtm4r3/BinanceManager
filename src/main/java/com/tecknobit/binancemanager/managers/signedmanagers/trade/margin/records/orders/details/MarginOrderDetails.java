package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.orders.details;

import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code MarginOrderDetails} class is useful to format a {@code "Binance"}'s margin order details
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data">
 * Query Cross Margin Account Details (USER_DATA)</a>
 * @see Order
 **/
public class MarginOrderDetails extends Order {

    /**
     * {@code isIsolated} is instance that memorizes if is isolated
     **/
    private final boolean isIsolated;

    /**
     * {@code origClientOrderId} is instance that memorizes origin client order id
     **/
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
     **/
    private final Status status;

    /**
     * {@code timeInForce} is instance that memorizes time in force of the order
     **/
    private final TimeInForce timeInForce;

    /**
     * {@code type} is instance that memorizes type of the order
     **/
    private final OrderType type;

    /**
     * {@code side} is instance that memorizes side of the order
     **/
    private final Side side;

    /** Constructor to init {@link MarginOrderDetails} object
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
    public MarginOrderDetails(String symbol, long orderId, String clientOrderId, boolean isIsolated, String origClientOrderId,
                              double price, double origQty, double executedQty, double cummulativeQuoteQty, Status status,
                              TimeInForce timeInForce, OrderType type, Side side) {
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
    }

    /**
     * Constructor to init {@link MarginOrderDetails} object
     *
     * @param marginOrder: margin order details as {@link JSONObject}
     **/
    public MarginOrderDetails(JSONObject marginOrder) {
        super(marginOrder);
        isIsolated = hOrder.getBoolean("isIsolated");
        origClientOrderId = hOrder.getString("origClientOrderId");
        price = hOrder.getDouble("price", 0);
        origQty = hOrder.getDouble("origQty", 0);
        executedQty = hOrder.getDouble("executedQty", 0);
        cummulativeQuoteQty = hOrder.getDouble("cummulativeQuoteQty", 0);
        status = Status.valueOf(hOrder.getString("status", Status.CONFIRMED.name()));
        timeInForce = TimeInForce.valueOf(hOrder.getString("timeInForce", TimeInForce.GTC.name()));
        type = OrderType.valueOf(hOrder.getString("type", OrderType.MARKET.name()));
        side = Side.valueOf(hOrder.getString("side", Side.BUY.name()));
    }

    /**
     * Method to get {@link #isIsolated} instance <br>
     * Any params required
     *
     * @return {@link #isIsolated} instance as boolean
     **/
    public boolean isIsolated() {
        return isIsolated;
    }

    /**
     * Method to get {@link #origClientOrderId} instance <br>
     * Any params required
     *
     * @return {@link #origClientOrderId} instance as {@link String}
     **/
    public String getOrigClientOrderId() {
        return origClientOrderId;
    }

    /**
     * Method to get {@link #price} instance <br>
     * Any params required
     *
     * @return {@link #price} instance as double
     **/
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

    /**
     * Method to get {@link #origQty} instance <br>
     * Any params required
     *
     * @return {@link #origQty} instance as double
     **/
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

    /**
     * Method to get {@link #executedQty} instance <br>
     * Any params required
     *
     * @return {@link #executedQty} instance as double
     **/
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

    /**
     * Method to get {@link #cummulativeQuoteQty} instance <br>
     * Any params required
     *
     * @return {@link #cummulativeQuoteQty} instance as double
     **/
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

    /**
     * Method to get {@link #status} instance <br>
     * Any params required
     *
     * @return {@link #status} instance as {@link Status}
     **/
    public Status getStatus() {
        return status;
    }

    /**
     * Method to get {@link #timeInForce} instance <br>
     * Any params required
     *
     * @return {@link #timeInForce} instance as {@link TimeInForce}
     **/
    public TimeInForce getTimeInForce() {
        return timeInForce;
    }

    /**
     * Method to get {@link #type} instance <br>
     * Any params required
     *
     * @return {@link #type} instance as {@link OrderType}
     **/
    public OrderType getType() {
        return type;
    }

    /**
     * Method to get {@link #side} instance <br>
     * Any params required
     *
     * @return {@link #side} instance as {@link Side}
     **/
    public Side getSide() {
        return side;
    }

    /**
     * Method to get {@code stop price} value <br>
     * Any params required
     *
     * @return stopPrice as double, if is a null field will return -1
     **/
    public double getStopPrice() {
        return hOrder.getDouble("stopPrice", -1);
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
        return hOrder.getDouble("icebergQty", -1);
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

}
