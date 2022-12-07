package com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.response;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;


/**
 * The {@code ResultSpotOrder} class is useful to format a {@code "Binance"}'s spot order in {@code "RESULT"} format
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
 * New Order (TRADE)</a>
 * @see Order
 * @see SpotOrder
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
     **/
    protected final Status status;

    /**
     * {@code timeInForce} is instance that memorizes time in force of the order
     **/
    protected final TimeInForce timeInForce;

    /**
     * {@code type} is instance that memorizes type of the order
     **/
    protected final OrderType type;

    /**
     * {@code side} is instance that memorizes side of the order
     **/
    protected final Side side;

    /**
     * {@code workingTime} indicating when the order started working on the order book
     **/
    protected final long workingTime;

    /**
     * {@code selfTradePreventionMode} is instance that contains the self trade prevention mode
     **/
    protected final String selfTradePreventionMode;

    /**
     * {@code trailingTime} indicating the time when the trailing order is active and tracking price changes,
     * will appear for the following order types (TAKE_PROFIT, TAKE_PROFIT_LIMIT, STOP_LOSS, STOP_LOSS_LIMIT
     * if trailingDelta parameter was provided)
     **/
    protected final long trailingTime;

    /**
     * Constructor to init {@link ResultSpotOrder} object
     *
     * @param symbol                  : symbol used in the order
     * @param orderId                 : order identifier
     * @param orderListId             : list order identifier
     * @param clientOrderId           : client order identifier
     * @param transactTime            : transaction time
     * @param price                   : price in order
     * @param origQty                 : origin quantity in order
     * @param executedQty             : executed quantity in order
     * @param cummulativeQuoteQty     : cummulative quote quantity
     * @param status                  : status of the order
     * @param timeInForce             : time in force of the order
     * @param type                    : type of the order
     * @param side                    : side of the order
     * @param workingTime             : indicating when the order started working on the order book
     * @param selfTradePreventionMode : self trade prevention mode
     * @param trailingTime:           indicating the time when the trailing order is active and tracking price changes
     **/
    public ResultSpotOrder(String symbol, long orderId, long orderListId, String clientOrderId, long transactTime,
                           double price, double origQty, double executedQty, double cummulativeQuoteQty,
                           Status status, TimeInForce timeInForce, OrderType type, Side side, long workingTime,
                           String selfTradePreventionMode, long trailingTime) {
        super(symbol, orderId, orderListId, clientOrderId, transactTime);
        this.price = price;
        this.origQty = origQty;
        this.executedQty = executedQty;
        this.cummulativeQuoteQty = cummulativeQuoteQty;
        this.status = status;
        this.timeInForce = timeInForce;
        this.type = type;
        this.side = side;
        this.workingTime = workingTime;
        this.selfTradePreventionMode = selfTradePreventionMode;
        this.trailingTime = trailingTime;
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
        status = Status.valueOf(hOrder.getString("status", Status.CONFIRMED.name()));
        timeInForce = TimeInForce.valueOf(hOrder.getString("timeInForce", TimeInForce.GTC.name()));
        type = OrderType.valueOf(hOrder.getString("type", OrderType.MARKET.name()));
        side = Side.valueOf(hOrder.getString("side", Side.BUY.name()));
        workingTime = hOrder.getLong("workingTime", 0);
        selfTradePreventionMode = hOrder.getString("selfTradePreventionMode");
        trailingTime = hOrder.getLong("trailingTime", 0);
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
        return roundValue(executedQty, decimals);
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
     * Method to get {@link #workingTime} instance <br>
     * Any params required
     *
     * @return {@link #workingTime} instance as long
     **/
    public long getWorkingTime() {
        return workingTime;
    }

    /**
     * Method to get {@link #workingTime} instance <br>
     * Any params required
     *
     * @return {@link #workingTime} instance as {@link Date}
     **/
    public Date getWorkingTimeDate() {
        return TimeFormatter.getDate(workingTime);
    }

    /**
     * Method to get {@link #selfTradePreventionMode} instance <br>
     * Any params required
     *
     * @return {@link #selfTradePreventionMode} instance as {@link String}
     **/
    public String getSelfTradePreventionMode() {
        return selfTradePreventionMode;
    }

    /**
     * Method to get {@link #trailingTime} instance <br>
     * Any params required
     *
     * @return {@link #trailingTime} instance as long
     **/
    public long getTrailingTime() {
        return trailingTime;
    }

    /**
     * Method to get {@link #trailingTime} instance <br>
     * Any params required
     *
     * @return {@link #trailingTime} instance as {@link Date}
     **/
    public Date getTrailingTimeDate() {
        return TimeFormatter.getDate(trailingTime);
    }

}
