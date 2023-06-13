package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.orders.response;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code MarginOrderStatus} class is useful to format a {@code "Binance"}'s {@code "STATUS"} response
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
 * Margin Account New Order (TRADE)</a>
 * @see Order
 * @see MarginOrder
 * @see ACKMarginOrder
 * @see ResultMarginOrder
 */
public class MarginOrderStatus extends ResultMarginOrder {

    /**
     * {@code icebergQty} is instance that memorizes iceberg quantity
     */
    private final double icebergQty;

    /**
     * {@code isWorking} is instance that memorizes if is working
     */
    private final boolean isWorking;

    /**
     * {@code stopPrice} is instance that memorizes stop price value
     */
    private final double stopPrice;

    /**
     * {@code time} is instance that memorizes time value
     */
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
     */
    public MarginOrderStatus(String symbol, long orderId, String clientOrderId, long updateTime, boolean isIsolated,
                             double price, double origQty, double executedQty, double cummulativeQuoteQty, Status status,
                             TimeInForce timeInForce, OrderType type, Side side, double icebergQty, boolean isWorking,
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
     */
    public MarginOrderStatus(JSONObject resultMarginOrder) {
        super(resultMarginOrder);
        icebergQty = resultMarginOrder.getDouble("icebergQty");
        isWorking = resultMarginOrder.getBoolean("isWorking");
        stopPrice = resultMarginOrder.getDouble("stopPrice");
        time = resultMarginOrder.getLong("time");
    }

    /**
     * Method to get {@link #icebergQty} instance <br>
     * No-any params required
     *
     * @return {@link #icebergQty} instance as double
     */
    public double getIcebergQty() {
        return icebergQty;
    }

    /**
     * Method to get {@link #icebergQty} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #icebergQty} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getIcebergQty(int decimals) {
        return roundValue(icebergQty, decimals);
    }

    /**
     * Method to get {@link #isWorking} instance <br>
     * No-any params required
     *
     * @return {@link #isWorking} instance as boolean
     */
    public boolean isWorking() {
        return isWorking;
    }

    /**
     * Method to get {@link #stopPrice} instance <br>
     * No-any params required
     *
     * @return {@link #stopPrice} instance as double
     */
    public double getStopPrice() {
        return stopPrice;
    }

    /**
     * Method to get {@link #stopPrice} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #stopPrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getStopPrice(int decimals) {
        return roundValue(stopPrice, decimals);
    }

    /**
     * Method to get {@link #time} instance <br>
     * No-any params required
     *
     * @return {@link #time} instance as long
     */
    public long getTime() {
        return time;
    }

    /**
     * Method to get {@link #time} instance <br>
     * No-any params required
     *
     * @return {@link #time} instance as {@link Date}
     */
    public Date getTransactionDate() {
        return TimeFormatter.getDate(time);
    }

    /**
     * Method to get {@link #transactTime} instance <br>
     * No-any params required
     *
     * @return {@link #transactTime} instance as long
     */
    public long getUpdateTime() {
        return transactTime;
    }

    /**
     * Method to get {@link #transactTime} instance <br>
     * No-any params required
     *
     * @return {@link #transactTime} instance as {@link Date}
     */
    public Date getUpdateDate() {
        return TimeFormatter.getDate(transactTime);
    }

}
