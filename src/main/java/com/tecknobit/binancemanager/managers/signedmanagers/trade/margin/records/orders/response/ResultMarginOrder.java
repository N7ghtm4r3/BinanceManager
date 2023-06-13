package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.orders.response;

import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code ResultMarginOrder} class is useful to format a {@code "Binance"}'s {@code "FULL"} response
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
 * Margin Account New Order (TRADE)</a>
 * @see Order
 * @see MarginOrder
 * @see ACKMarginOrder
 */
public class ResultMarginOrder extends ACKMarginOrder {

    /**
     * {@code price} is instance that memorizes price in order
     */
    protected final double price;

    /**
     * {@code origQty} is instance that memorizes origin quantity in order
     */
    protected final double origQty;

    /**
     * {@code executedQty} is instance that memorizes executed quantity in order
     */
    protected final double executedQty;

    /**
     * {@code cummulativeQuoteQty} is instance that memorizes cummulative quote quantity in order
     */
    protected final double cummulativeQuoteQty;

    /**
     * {@code status} is instance that memorizes status of the order
     */
    protected final Status status;

    /**
     * {@code timeInForce} is instance that memorizes time in force of the order
     */
    protected final TimeInForce timeInForce;

    /**
     * {@code type} is instance that memorizes type of the order
     */
    protected final OrderType type;

    /**
     * {@code type} is instance that memorizes side of the order
     */
    protected final Side side;

    /** Constructor to init {@link ResultMarginOrder} object
     * @param symbol: symbol used in the order
     * @param orderId: order identifier
     * @param clientOrderId: client order identifier
     * @param transactTime: transaction time
     * @param isIsolated: is isolated
     * @param price: price in order
     * @param origQty: origin quantity in order
     * @param executedQty: executed quantity in order
     * @param cummulativeQuoteQty: cummulative quote quantity
     * @param status: status of the order
     * @param timeInForce: time in force of the order
     * @param type: type of the order
     * @param side: side of the order
     */
    public ResultMarginOrder(String symbol, long orderId, String clientOrderId, long transactTime, boolean isIsolated,
                             double price, double origQty, double executedQty, double cummulativeQuoteQty, Status status,
                             TimeInForce timeInForce, OrderType type, Side side) {
        super(symbol, orderId, clientOrderId, transactTime, isIsolated);
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
     * Constructor to init {@link ResultMarginOrder} object
     *
     * @param resultMarginOrder: result margin order details as {@link JSONObject}
     */
    public ResultMarginOrder(JSONObject resultMarginOrder) {
        super(resultMarginOrder);
        price = hOrder.getDouble("price", 0);
        origQty = hOrder.getDouble("origQty", 0);
        executedQty = hOrder.getDouble("executedQty", 0);
        cummulativeQuoteQty = hOrder.getDouble("cumulativeQuoteQty", 0);
        status = Status.valueOf(hOrder.getString("status", Status.CONFIRMED.name()));
        timeInForce = TimeInForce.valueOf(hOrder.getString("timeInForce", TimeInForce.GTC.name()));
        type = OrderType.valueOf(hOrder.getString("type", OrderType.MARKET.name()));
        side = Side.valueOf(hOrder.getString("side", Side.BUY.name()));
    }

    /**
     * Method to get {@link #price} instance <br>
     * No-any params required
     *
     * @return {@link #price} instance as double
     */
    public double getPrice() {
        return price;
    }

    /**
     * Method to get {@link #price} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #price} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getPrice(int decimals) {
        return roundValue(price, decimals);
    }

    /**
     * Method to get {@link #origQty} instance <br>
     * No-any params required
     *
     * @return {@link #origQty} instance as double
     */
    public double getOrigQty() {
        return origQty;
    }

    /**
     * Method to get {@link #origQty} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #origQty} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getOrigQty(int decimals) {
        return roundValue(origQty, decimals);
    }

    /**
     * Method to get {@link #executedQty} instance <br>
     * No-any params required
     *
     * @return {@link #executedQty} instance as double
     */
    public double getExecutedQty() {
        return executedQty;
    }

    /**
     * Method to get {@link #executedQty} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #executedQty} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getExecutedQty(int decimals) {
        return roundValue(origQty, decimals);
    }

    /**
     * Method to get {@link #cummulativeQuoteQty} instance <br>
     * No-any params required
     *
     * @return {@link #cummulativeQuoteQty} instance as double
     */
    public double getCummulativeQuoteQty() {
        return cummulativeQuoteQty;
    }

    /**
     * Method to get {@link #cummulativeQuoteQty} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #cummulativeQuoteQty} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getCummulativeQuoteQty(int decimals) {
        return roundValue(cummulativeQuoteQty, decimals);
    }

    /**
     * Method to get {@link #status} instance <br>
     * No-any params required
     *
     * @return {@link #status} instance as {@link Status}
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Method to get {@link #timeInForce} instance <br>
     * No-any params required
     *
     * @return {@link #timeInForce} instance as {@link TimeInForce}
     */
    public TimeInForce getTimeInForce() {
        return timeInForce;
    }

    /**
     * Method to get {@link #type} instance <br>
     * No-any params required
     *
     * @return {@link #type} instance as {@link OrderType}
     */
    public OrderType getType() {
        return type;
    }

    /**
     * Method to get {@link #side} instance <br>
     * No-any params required
     *
     * @return {@link #side} instance as {@link Side}
     */
    public Side getSide() {
        return side;
    }

}
