package com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.userdatastreams.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.market.records.stats.ExchangeInformation.SelfTradePreventionMode;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.OrderType;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Side;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Status;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.TimeInForce;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code OrderUpdate} class is useful to format a user order update
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#payload-order-update">
 * Payload: Order Update</a>
 * @see BinanceItem
 * @see BinanceWebsocketResponse
 **/
public class OrderUpdate extends BinanceWebsocketResponse {

    /**
     * {@code ExecutionType} list of available execution types
     **/
    public enum ExecutionType {

        /**
         * {@code NEW} execution type
         **/
        NEW,

        /**
         * {@code CANCELED} execution type
         **/
        CANCELED,

        /**
         * {@code REPLACED} execution type
         **/
        REPLACED,

        /**
         * {@code REJECTED} execution type
         **/
        REJECTED,

        /**
         * {@code TRADE} execution type
         **/
        TRADE,

        /**
         * {@code EXPIRED} execution type
         **/
        EXPIRED,

        /**
         * {@code TRADE_PREVENTION} execution type
         **/
        TRADE_PREVENTION

    }

    /**
     * {@code symbol} of the order update
     **/
    private final String symbol;

    /**
     * {@code clientOrderId} client order id of the order update
     **/
    private final String clientOrderId;

    /**
     * {@code side} of the order update
     **/
    private final Side side;

    /**
     * {@code type} of the order update
     **/
    private final OrderType type;

    /**
     * {@code timeInForce} time in force of the order update
     **/
    private final TimeInForce timeInForce;

    /**
     * {@code orderQuantity} order quantity of the order update
     **/
    private final double orderQuantity;

    /**
     * {@code orderPrice} order price of the order update
     **/
    private final double orderPrice;

    /**
     * {@code stopPrice} stop price of the order update
     **/
    private final double stopPrice;

    /**
     * {@code trailingDelta} trailing delta of the order update
     **/
    private final double trailingDelta;

    /**
     * {@code icebergQuantity} iceberg quantity of the order update
     **/
    private final double icebergQuantity;

    /**
     * {@code orderListId} order list id of the order update
     **/
    private final long orderListId;

    /**
     * {@code originalClientOrderId} original client order id of the order update
     **/
    private final long originalClientOrderId;

    /**
     * {@code currentExecutionType} current execution type of the order update
     **/
    private final ExecutionType currentExecutionType;

    /**
     * {@code currentOrderStatus} current order status of the order update
     **/
    private final Status currentOrderStatus;

    /**
     * {@code orderRejectReason} order reject reason of the order update
     **/
    private final String orderRejectReason;

    /**
     * {@code orderId} id of the order update
     **/
    private final long orderId;

    /**
     * {@code lastExecutedQuantity} last executed quantity of the order update
     **/
    private final double lastExecutedQuantity;

    /**
     * {@code lastCumulativeFilledQuantity} last cumulative filled quantity of the order update
     **/
    private final double lastCumulativeFilledQuantity;

    /**
     * {@code lastExecutedPrice} last executed price of the order update
     **/
    private final double lastExecutedPrice;

    /**
     * {@code commissionAmount} commission amount of the order update
     **/
    private final double commissionAmount;

    /**
     * {@code commissionAsset} commission asset of the order update
     **/
    private final String commissionAsset;

    /**
     * {@code transactionTime} transaction time of the order update
     **/
    private final long transactionTime;

    /**
     * {@code tradeId} trade id of the order update
     **/
    private final long tradeId;

    /**
     * {@code preventedMatchId} prevented match id of the order update
     **/
    private final long preventedMatchId;

    /**
     * {@code isInTheOrderBook} whether the order update is in the book
     **/
    private final boolean isInTheOrderBook;

    /**
     * {@code isTradeMakerSide} whether the order update is trade maker side
     **/
    private final boolean isTradeMakerSide;

    /**
     * {@code orderCreationTime} order creation time of the order update
     **/
    private final long orderCreationTime;

    /**
     * {@code cumulativeQuoteAssetTransactedQuantity} cumulative quote asset transacted quantity of the order update
     **/
    private final double cumulativeQuoteAssetTransactedQuantity;

    /**
     * {@code lastQuoteAssetTransactedQuantity} last quote asset transacted quantity of the order update
     **/
    private final double lastQuoteAssetTransactedQuantity;

    /**
     * {@code quoteOrderQuantity} quote order quantity of the order update
     **/
    private final double quoteOrderQuantity;

    /**
     * {@code trailingTime} trailing time of the order update
     **/
    private final long trailingTime;

    /**
     * {@code strategyId} strategy id of the order update
     **/
    private final long strategyId;

    /**
     * {@code strategyType} strategy type of the order update
     **/
    private final long strategyType;

    /**
     * {@code workingTime} working time of the order update
     **/
    private final long workingTime;

    /**
     * {@code selfTradePreventionMode} self trade prevention mode of the order update
     **/
    private final SelfTradePreventionMode selfTradePreventionMode;

    /**
     * {@code tradeGroupId} trade group id of the order update
     **/
    private final long tradeGroupId;

    /**
     * {@code counterOrderId} counter order id of the order update
     **/
    private final long counterOrderId;

    /**
     * {@code preventedQuantity} prevented quantity of the order update
     **/
    private final double preventedQuantity;

    /**
     * {@code lastPreventedQuantity} last prevented quantity of the order update
     **/
    private final double lastPreventedQuantity;

    /**
     * {@code listStatus} list status of the order update
     **/
    private final ListStatus listStatus;

    /**
     * Constructor to init {@link OrderUpdate} object
     *
     * @param eventType:                              event type of the balance update
     * @param eventTime:                              event time of the balance update
     * @param symbol:                                 symbol of the order update
     * @param clientOrderId:                          client order id of the order update
     * @param side:                                   side of the order update
     * @param type:                                   type of the order update
     * @param timeInForce:                            time in force of the order update
     * @param orderQuantity:                          order quality of the order update
     * @param orderPrice:                             order price of the order update
     * @param stopPrice:                              stop price of the order update
     * @param trailingDelta:                          trailing delta of the order update
     * @param icebergQuantity:                        iceberg quantity of the order update
     * @param orderListId:                            order list id of the order update
     * @param originalClientOrderId:                  original client order id of the order update
     * @param currentExecutionType:                   current execution type of the order update
     * @param currentOrderStatus:                     current order status of the order update
     * @param orderRejectReason:                      order reject reason of the order update
     * @param orderId:                                order id of the order update
     * @param lastExecutedQuantity:                   last executed quantity of the order update
     * @param lastCumulativeFilledQuantity:           last cumulative filled quantity of the order update
     * @param lastExecutedPrice:                      last executed price of the order update
     * @param commissionAmount:                       commission amount of the order update
     * @param commissionAsset:                        commission asset of the order update
     * @param transactionTime:                        transaction time of the order update
     * @param tradeId:                                trade id of the order update
     * @param preventedMatchId:                       prevent match id of the order update
     * @param isInTheOrderBook:                       whether the order update is in the book
     * @param isTradeMakerSide:                       whether the order update is trade maker side
     * @param orderCreationTime:                      order creation time of the order update
     * @param cumulativeQuoteAssetTransactedQuantity: cumulative quote asset transacted quantity of the order update
     * @param lastQuoteAssetTransactedQuantity:       last quote asset transacted quantity of the order update
     * @param quoteOrderQuantity:                     quote order quantity of the order update
     * @param trailingTime:                           trailing time of the order update
     * @param strategyId:                             strategy id of the order update
     * @param strategyType:                           strategy type of the order update
     * @param workingTime:                            working time of the order update
     * @param selfTradePreventionMode:                self trade prevention mode of the order update
     * @param tradeGroupId:                           trade group id of the order update
     * @param counterOrderId:                         counter order id of the order update
     * @param preventedQuantity:                      prevented quantity of the order update
     * @param lastPreventedQuantity:                  last prevented quantity of the order update
     * @param listStatus:                             list status of the order update
     **/
    public OrderUpdate(EventType eventType, long eventTime, String symbol, String clientOrderId, Side side, OrderType type,
                       TimeInForce timeInForce, double orderQuantity, double orderPrice, double stopPrice,
                       double trailingDelta, double icebergQuantity, long orderListId, long originalClientOrderId,
                       ExecutionType currentExecutionType, Status currentOrderStatus, String orderRejectReason,
                       long orderId, double lastExecutedQuantity, double lastCumulativeFilledQuantity,
                       double lastExecutedPrice, double commissionAmount, String commissionAsset, long transactionTime,
                       long tradeId, long preventedMatchId, boolean isInTheOrderBook, boolean isTradeMakerSide,
                       long orderCreationTime, double cumulativeQuoteAssetTransactedQuantity,
                       double lastQuoteAssetTransactedQuantity, double quoteOrderQuantity, long trailingTime,
                       long strategyId, long strategyType, long workingTime,
                       SelfTradePreventionMode selfTradePreventionMode, long tradeGroupId, long counterOrderId,
                       double preventedQuantity, double lastPreventedQuantity, ListStatus listStatus) {
        super(eventType, eventTime);
        this.symbol = symbol;
        this.clientOrderId = clientOrderId;
        this.side = side;
        this.type = type;
        this.timeInForce = timeInForce;
        this.orderQuantity = orderQuantity;
        this.orderPrice = orderPrice;
        this.stopPrice = stopPrice;
        this.trailingDelta = trailingDelta;
        this.icebergQuantity = icebergQuantity;
        this.orderListId = orderListId;
        this.originalClientOrderId = originalClientOrderId;
        this.currentExecutionType = currentExecutionType;
        this.currentOrderStatus = currentOrderStatus;
        this.orderRejectReason = orderRejectReason;
        this.orderId = orderId;
        this.lastExecutedQuantity = lastExecutedQuantity;
        this.lastCumulativeFilledQuantity = lastCumulativeFilledQuantity;
        this.lastExecutedPrice = lastExecutedPrice;
        this.commissionAmount = commissionAmount;
        this.commissionAsset = commissionAsset;
        this.transactionTime = transactionTime;
        this.tradeId = tradeId;
        this.preventedMatchId = preventedMatchId;
        this.isInTheOrderBook = isInTheOrderBook;
        this.isTradeMakerSide = isTradeMakerSide;
        this.orderCreationTime = orderCreationTime;
        this.cumulativeQuoteAssetTransactedQuantity = cumulativeQuoteAssetTransactedQuantity;
        this.lastQuoteAssetTransactedQuantity = lastQuoteAssetTransactedQuantity;
        this.quoteOrderQuantity = quoteOrderQuantity;
        this.trailingTime = trailingTime;
        this.strategyId = strategyId;
        this.strategyType = strategyType;
        this.workingTime = workingTime;
        this.selfTradePreventionMode = selfTradePreventionMode;
        this.tradeGroupId = tradeGroupId;
        this.counterOrderId = counterOrderId;
        this.preventedQuantity = preventedQuantity;
        this.lastPreventedQuantity = lastPreventedQuantity;
        this.listStatus = listStatus;
    }

    /**
     * Constructor to init {@link OrderUpdate} object
     *
     * @param jOrderUpdate: order update details as {@link JSONObject}
     **/
    public OrderUpdate(JSONObject jOrderUpdate) {
        super(jOrderUpdate);
        symbol = hItem.getString("s");
        clientOrderId = hItem.getString("c");
        side = Side.valueOf(hItem.getString("S"));
        type = OrderType.valueOf(hItem.getString("o"));
        timeInForce = TimeInForce.valueOf(hItem.getString("f"));
        orderQuantity = hItem.getDouble("q", 0);
        orderPrice = hItem.getDouble("p", 0);
        stopPrice = hItem.getDouble("P", 0);
        trailingDelta = hItem.getDouble("d", 0);
        icebergQuantity = hItem.getDouble("F", 0);
        orderListId = hItem.getLong("g", -1);
        originalClientOrderId = hItem.getLong("C", -1);
        currentExecutionType = ExecutionType.valueOf(hItem.getString("x"));
        currentOrderStatus = Status.valueOf(hItem.getString("X"));
        orderRejectReason = hItem.getString("r");
        orderId = hItem.getLong("i", -1);
        lastExecutedQuantity = hItem.getDouble("l", 0);
        lastCumulativeFilledQuantity = hItem.getDouble("z", 0);
        lastExecutedPrice = hItem.getDouble("L", 0);
        commissionAmount = hItem.getDouble("n", 0);
        commissionAsset = hItem.getString("N");
        transactionTime = hItem.getLong("T", -1);
        tradeId = hItem.getLong("t", -1);
        preventedMatchId = hItem.getLong("v", -1);
        isInTheOrderBook = hItem.getBoolean("w");
        isTradeMakerSide = hItem.getBoolean("m");
        orderCreationTime = hItem.getLong("O", -1);
        cumulativeQuoteAssetTransactedQuantity = hItem.getDouble("Z", 0);
        lastQuoteAssetTransactedQuantity = hItem.getDouble("Y", 0);
        quoteOrderQuantity = hItem.getDouble("Q", 0);
        trailingTime = hItem.getLong("D", -1);
        strategyId = hItem.getLong("j", -1);
        strategyType = hItem.getLong("J", -1);
        workingTime = hItem.getLong("W", -1);
        selfTradePreventionMode = SelfTradePreventionMode.valueOf(hItem.getString("V"));
        tradeGroupId = hItem.getLong("u", -1);
        counterOrderId = hItem.getLong("U", -1);
        preventedQuantity = hItem.getDouble("A", 0);
        lastPreventedQuantity = hItem.getDouble("B", 0);
        JSONObject jList = hItem.getJSONObject("listStatus");
        if (jList != null)
            listStatus = new ListStatus(jList);
        else
            listStatus = null;
    }

    /**
     * Method to get {@link #symbol} instance <br>
     * No-any params required
     *
     * @return {@link #symbol} instance as {@link String}
     **/
    public String getSymbol() {
        return symbol;
    }

    /**
     * Method to get {@link #clientOrderId} instance <br>
     * No-any params required
     *
     * @return {@link #clientOrderId} instance as {@link String}
     **/
    public String getClientOrderId() {
        return clientOrderId;
    }

    /**
     * Method to get {@link #side} instance <br>
     * No-any params required
     *
     * @return {@link #side} instance as {@link Side}
     **/
    public Side getSide() {
        return side;
    }

    /**
     * Method to get {@link #type} instance <br>
     * No-any params required
     *
     * @return {@link #type} instance as {@link OrderType}
     **/
    public OrderType getType() {
        return type;
    }

    /**
     * Method to get {@link #timeInForce} instance <br>
     * No-any params required
     *
     * @return {@link #timeInForce} instance as {@link TimeInForce}
     **/
    public TimeInForce getTimeInForce() {
        return timeInForce;
    }

    /**
     * Method to get {@link #orderQuantity} instance <br>
     * No-any params required
     *
     * @return {@link #orderQuantity} instance as double
     **/
    public double getOrderQuantity() {
        return orderQuantity;
    }

    /**
     * Method to get {@link #orderQuantity} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #orderQuantity} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getOrderQuantity(int decimals) {
        return roundValue(orderQuantity, decimals);
    }

    /**
     * Method to get {@link #orderPrice} instance <br>
     * No-any params required
     *
     * @return {@link #orderPrice} instance as double
     **/
    public double getOrderPrice() {
        return orderPrice;
    }

    /**
     * Method to get {@link #orderPrice} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #orderPrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getOrderPrice(int decimals) {
        return roundValue(orderPrice, decimals);
    }

    /**
     * Method to get {@link #stopPrice} instance <br>
     * No-any params required
     *
     * @return {@link #stopPrice} instance as double
     **/
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

    /**
     * Method to get {@link #trailingDelta} instance <br>
     * No-any params required
     *
     * @return {@link #trailingDelta} instance as double
     **/
    public double getTrailingDelta() {
        return trailingDelta;
    }

    /**
     * Method to get {@link #trailingDelta} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #trailingDelta} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTrailingDelta(int decimals) {
        return roundValue(trailingDelta, decimals);
    }

    /**
     * Method to get {@link #icebergQuantity} instance <br>
     * No-any params required
     *
     * @return {@link #icebergQuantity} instance as double
     **/
    public double getIcebergQuantity() {
        return icebergQuantity;
    }

    /**
     * Method to get {@link #icebergQuantity} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #icebergQuantity} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getIcebergQuantity(int decimals) {
        return roundValue(icebergQuantity, decimals);
    }

    /**
     * Method to get {@link #orderListId} instance <br>
     * No-any params required
     *
     * @return {@link #orderListId} instance as long
     **/
    public long getOrderListId() {
        return orderListId;
    }

    /**
     * Method to get {@link #originalClientOrderId} instance <br>
     * No-any params required
     *
     * @return {@link #originalClientOrderId} instance as long
     **/
    public long getOriginalClientOrderId() {
        return originalClientOrderId;
    }

    /**
     * Method to get {@link #currentExecutionType} instance <br>
     * No-any params required
     *
     * @return {@link #currentExecutionType} instance as {@link ExecutionType}
     **/
    public ExecutionType getCurrentExecutionType() {
        return currentExecutionType;
    }

    /**
     * Method to get {@link #currentOrderStatus} instance <br>
     * No-any params required
     *
     * @return {@link #currentOrderStatus} instance as {@link Status}
     **/
    public Status getCurrentOrderStatus() {
        return currentOrderStatus;
    }

    /**
     * Method to get {@link #orderRejectReason} instance <br>
     * No-any params required
     *
     * @return {@link #orderRejectReason} instance as {@link String}
     **/
    public String getOrderRejectReason() {
        return orderRejectReason;
    }

    /**
     * Method to get {@link #orderId} instance <br>
     * No-any params required
     *
     * @return {@link #orderId} instance as long
     **/
    public long getOrderId() {
        return orderId;
    }

    /**
     * Method to get {@link #lastExecutedQuantity} instance <br>
     * No-any params required
     *
     * @return {@link #lastExecutedQuantity} instance as double
     **/
    public double getLastExecutedQuantity() {
        return lastExecutedQuantity;
    }

    /**
     * Method to get {@link #lastExecutedQuantity} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #lastExecutedQuantity} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getLastExecutedQuantity(int decimals) {
        return roundValue(lastExecutedQuantity, decimals);
    }

    /**
     * Method to get {@link #lastCumulativeFilledQuantity} instance <br>
     * No-any params required
     *
     * @return {@link #lastCumulativeFilledQuantity} instance as double
     **/
    public double getLastCumulativeFilledQuantity() {
        return lastCumulativeFilledQuantity;
    }

    /**
     * Method to get {@link #lastCumulativeFilledQuantity} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #lastCumulativeFilledQuantity} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getLastCumulativeFilledQuantity(int decimals) {
        return roundValue(lastCumulativeFilledQuantity, decimals);
    }

    /**
     * Method to get {@link #lastExecutedPrice} instance <br>
     * No-any params required
     *
     * @return {@link #lastExecutedPrice} instance as double
     **/
    public double getLastExecutedPrice() {
        return lastExecutedPrice;
    }

    /**
     * Method to get {@link #lastExecutedPrice} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #lastExecutedPrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getLastExecutedPrice(int decimals) {
        return roundValue(lastExecutedPrice, decimals);
    }

    /**
     * Method to get {@link #commissionAmount} instance <br>
     * No-any params required
     *
     * @return {@link #commissionAmount} instance as double
     **/
    public double getCommissionAmount() {
        return commissionAmount;
    }

    /**
     * Method to get {@link #commissionAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #commissionAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getCommissionAmount(int decimals) {
        return roundValue(commissionAmount, decimals);
    }

    /**
     * Method to get {@link #commissionAsset} instance <br>
     * No-any params required
     *
     * @return {@link #commissionAsset} instance as {@link String}
     **/
    public String getCommissionAsset() {
        return commissionAsset;
    }

    /**
     * Method to get {@link #transactionTime} instance <br>
     * No-any params required
     *
     * @return {@link #transactionTime} instance as long
     **/
    public long getTransactionTime() {
        return transactionTime;
    }

    /**
     * Method to get {@link #transactionTime} instance <br>
     * No-any params required
     *
     * @return {@link #transactionTime} instance as {@link Date}
     **/
    public Date getTransactionDate() {
        return TimeFormatter.getDate(transactionTime);
    }

    /**
     * Method to get {@link #tradeId} instance <br>
     * No-any params required
     *
     * @return {@link #tradeId} instance as long
     **/
    public long getTradeId() {
        return tradeId;
    }

    /**
     * Method to get {@link #preventedMatchId} instance <br>
     * No-any params required
     *
     * @return {@link #preventedMatchId} instance as long
     **/
    public long getPreventedMatchId() {
        return preventedMatchId;
    }

    /**
     * Method to get {@link #isInTheOrderBook} instance <br>
     * No-any params required
     *
     * @return {@link #isInTheOrderBook} instance as boolean
     **/
    public boolean isInTheOrderBook() {
        return isInTheOrderBook;
    }

    /**
     * Method to get {@link #isTradeMakerSide} instance <br>
     * No-any params required
     *
     * @return {@link #isTradeMakerSide} instance as boolean
     **/
    public boolean isTradeMakerSide() {
        return isTradeMakerSide;
    }

    /**
     * Method to get {@link #orderCreationTime} instance <br>
     * No-any params required
     *
     * @return {@link #orderCreationTime} instance as long
     **/
    public long getOrderCreationTime() {
        return orderCreationTime;
    }

    /**
     * Method to get {@link #orderCreationTime} instance <br>
     * No-any params required
     *
     * @return {@link #orderCreationTime} instance as {@link Date}
     **/
    public Date getOrderCreationDate() {
        return TimeFormatter.getDate(orderCreationTime);
    }

    /**
     * Method to get {@link #cumulativeQuoteAssetTransactedQuantity} instance <br>
     * No-any params required
     *
     * @return {@link #cumulativeQuoteAssetTransactedQuantity} instance as double
     **/
    public double getCumulativeQuoteAssetTransactedQuantity() {
        return cumulativeQuoteAssetTransactedQuantity;
    }

    /**
     * Method to get {@link #cumulativeQuoteAssetTransactedQuantity} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #cumulativeQuoteAssetTransactedQuantity} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getCumulativeQuoteAssetTransactedQuantity(int decimals) {
        return roundValue(cumulativeQuoteAssetTransactedQuantity, decimals);
    }

    /**
     * Method to get {@link #lastQuoteAssetTransactedQuantity} instance <br>
     * No-any params required
     *
     * @return {@link #lastQuoteAssetTransactedQuantity} instance as double
     **/
    public double getLastQuoteAssetTransactedQuantity() {
        return lastQuoteAssetTransactedQuantity;
    }

    /**
     * Method to get {@link #lastQuoteAssetTransactedQuantity} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #lastQuoteAssetTransactedQuantity if decimalDigits is negative
     **/
    public double getLastQuoteAssetTransactedQuantity(int decimals) {
        return roundValue(lastQuoteAssetTransactedQuantity, decimals);
    }

    /**
     * Method to get {@link #quoteOrderQuantity} instance <br>
     * No-any params required
     *
     * @return {@link #quoteOrderQuantity} instance as double
     **/
    public double getQuoteOrderQuantity() {
        return quoteOrderQuantity;
    }

    /**
     * Method to get {@link #quoteOrderQuantity} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #quoteOrderQuantity} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getQuoteOrderQuantity(int decimals) {
        return roundValue(quoteOrderQuantity, decimals);
    }

    /**
     * Method to get {@link #trailingTime} instance <br>
     * No-any params required
     *
     * @return {@link #trailingTime} instance as long
     **/
    public long getTrailingTime() {
        return trailingTime;
    }

    /**
     * Method to get {@link #trailingTime} instance <br>
     * No-any params required
     *
     * @return {@link #trailingTime} instance as {@link Date}
     **/
    public Date getTrailingDate() {
        return TimeFormatter.getDate(trailingTime);
    }

    /**
     * Method to get {@link #strategyId} instance <br>
     * No-any params required
     *
     * @return {@link #strategyId} instance as long
     **/
    public long getStrategyId() {
        return strategyId;
    }

    /**
     * Method to get {@link #strategyType} instance <br>
     * No-any params required
     *
     * @return {@link #strategyType} instance as long
     **/
    public long getStrategyType() {
        return strategyType;
    }

    /**
     * Method to get {@link #workingTime} instance <br>
     * No-any params required
     *
     * @return {@link #workingTime} instance as long
     **/
    public long getWorkingTime() {
        return workingTime;
    }

    /**
     * Method to get {@link #workingTime} instance <br>
     * No-any params required
     *
     * @return {@link #workingTime} instance as {@link Date}
     **/
    public Date getWorkingDate() {
        return TimeFormatter.getDate(workingTime);
    }

    /**
     * Method to get {@link #selfTradePreventionMode} instance <br>
     * No-any params required
     *
     * @return {@link #selfTradePreventionMode} instance as {@link SelfTradePreventionMode}
     **/
    public SelfTradePreventionMode getSelfTradePreventionMode() {
        return selfTradePreventionMode;
    }

    /**
     * Method to get {@link #tradeGroupId} instance <br>
     * No-any params required
     *
     * @return {@link #tradeGroupId} instance as long
     **/
    public long getTradeGroupId() {
        return tradeGroupId;
    }

    /**
     * Method to get {@link #counterOrderId} instance <br>
     * No-any params required
     *
     * @return {@link #counterOrderId} instance as long
     **/
    public long getCounterOrderId() {
        return counterOrderId;
    }

    /**
     * Method to get {@link #preventedQuantity} instance <br>
     * No-any params required
     *
     * @return {@link #preventedQuantity} instance as double
     **/
    public double getPreventedQuantity() {
        return preventedQuantity;
    }

    /**
     * Method to get {@link #preventedQuantity} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #preventedQuantity} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getPreventedQuantity(int decimals) {
        return roundValue(preventedQuantity, decimals);
    }

    /**
     * Method to get {@link #lastPreventedQuantity} instance <br>
     * No-any params required
     *
     * @return {@link #lastPreventedQuantity} instance as double
     **/
    public double getLastPreventedQuantity() {
        return lastPreventedQuantity;
    }

    /**
     * Method to get {@link #lastPreventedQuantity} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #lastPreventedQuantity} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getLastPreventedQuantity(int decimals) {
        return roundValue(lastPreventedQuantity, decimals);
    }

    /**
     * Method to get {@link #listStatus} instance <br>
     * No-any params required
     *
     * @return {@link #listStatus} instance as {@link ListStatus}
     **/
    public ListStatus getListStatus() {
        return listStatus;
    }

    /**
     * The {@code ListStatus} class is useful to format a list status
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see BinanceWebsocketResponse
     **/
    public static class ListStatus extends BinanceWebsocketResponse {

        /**
         * {@code symbol} of the list status
         **/
        private final String symbol;

        /**
         * {@code orderListId} order list id of the list status
         **/
        private final long orderListId;

        /**
         * {@code contingencyType} contingency type of the list status
         **/
        private final String contingencyType;

        /**
         * {@code listStatusType} list status type of the list status
         **/
        private final String listStatusType;

        /**
         * {@code listOrderStatus} list order status of the list status
         **/
        private final Status listOrderStatus;

        /**
         * {@code listRejectReason} list reject reason of the list status
         **/
        private final String listRejectReason;

        /**
         * {@code listClientOrderId} list client order id of the list status
         **/
        private final String listClientOrderId;

        /**
         * {@code transactionTime} transaction time of the list status
         **/
        private final long transactionTime;

        /**
         * {@code orders} of the list status
         **/
        private final ArrayList<Order> orders;

        /**
         * Constructor to init {@link ListStatus} object
         *
         * @param eventType:         symbol of the list status
         * @param eventTime:         event time of the list status
         * @param symbol:            list status details as {@link JSONObject}
         * @param orderListId:       order list id of the list status
         * @param contingencyType:   contingency type of the list status
         * @param listStatusType:    list order status of the list status
         * @param listOrderStatus:   list reject reason of the list status
         * @param listRejectReason:  list client order id of the list status
         * @param listClientOrderId: list client order id of the list status
         * @param transactionTime:   transaction time of the list status
         * @param orders:            orders of the list status
         **/
        public ListStatus(EventType eventType, long eventTime, String symbol, long orderListId, String contingencyType,
                          String listStatusType, Status listOrderStatus, String listRejectReason, String listClientOrderId,
                          long transactionTime, ArrayList<Order> orders) {
            super(eventType, eventTime);
            this.symbol = symbol;
            this.orderListId = orderListId;
            this.contingencyType = contingencyType;
            this.listStatusType = listStatusType;
            this.listOrderStatus = listOrderStatus;
            this.listRejectReason = listRejectReason;
            this.listClientOrderId = listClientOrderId;
            this.transactionTime = transactionTime;
            this.orders = orders;
        }

        /**
         * Constructor to init {@link ListStatus} object
         *
         * @param jListStatus: list status details as {@link JSONObject}
         **/
        public ListStatus(JSONObject jListStatus) {
            super(jListStatus);
            symbol = hItem.getString("s");
            orderListId = hItem.getLong("g", 0);
            contingencyType = hItem.getString("c");
            listStatusType = hItem.getString("l");
            listOrderStatus = Status.valueOf(hItem.getString("L"));
            listRejectReason = hItem.getString("r");
            listClientOrderId = hItem.getString("C");
            transactionTime = hItem.getLong("T", 0);
            JSONArray jOrders = hItem.getJSONArray("O", new JSONArray());
            jOrders = new JSONArray(jOrders.toString().replaceAll("\"s\"", "symbol")
                    .replaceAll("\"i\"", "orderId")
                    .replaceAll("\"c\"", "clientOrderId"));
            orders = new ArrayList<>();
            for (int j = 0; j < jOrders.length(); j++)
                orders.add(new Order(jOrders.getJSONObject(j)));
        }

        /**
         * Method to get {@link #symbol} instance <br>
         * No-any params required
         *
         * @return {@link #symbol} instance as {@link String}
         **/
        public String getSymbol() {
            return symbol;
        }

        /**
         * Method to get {@link #orderListId} instance <br>
         * No-any params required
         *
         * @return {@link #orderListId} instance as long
         **/
        public long getOrderListId() {
            return orderListId;
        }

        /**
         * Method to get {@link #contingencyType} instance <br>
         * No-any params required
         *
         * @return {@link #contingencyType} instance as {@link String}
         **/
        public String getContingencyType() {
            return contingencyType;
        }

        /**
         * Method to get {@link #listStatusType} instance <br>
         * No-any params required
         *
         * @return {@link #listStatusType} instance as {@link String}
         **/
        public String getListStatusType() {
            return listStatusType;
        }

        /**
         * Method to get {@link #listOrderStatus} instance <br>
         * No-any params required
         *
         * @return {@link #listOrderStatus} instance as {@link String}
         **/
        public Status getListOrderStatus() {
            return listOrderStatus;
        }

        /**
         * Method to get {@link #listRejectReason} instance <br>
         * No-any params required
         *
         * @return {@link #listRejectReason} instance as {@link String}
         **/
        public String getListRejectReason() {
            return listRejectReason;
        }

        /**
         * Method to get {@link #listClientOrderId} instance <br>
         * No-any params required
         *
         * @return {@link #listClientOrderId} instance as {@link String}
         **/
        public String getListClientOrderId() {
            return listClientOrderId;
        }

        /**
         * Method to get {@link #transactionTime} instance <br>
         * No-any params required
         *
         * @return {@link #transactionTime} instance as long
         **/
        public long getTransactionTime() {
            return transactionTime;
        }

        /**
         * Method to get {@link #transactionTime} instance <br>
         * No-any params required
         *
         * @return {@link #transactionTime} instance as {@link Date}
         **/
        public Date getTransactionDate() {
            return TimeFormatter.getDate(transactionTime);
        }

        /**
         * Method to get {@link #orders} instance <br>
         * No-any params required
         *
         * @return {@link #orders} instance as {@link ArrayList} of {@link Order}
         **/
        public ArrayList<Order> getOrders() {
            return orders;
        }

    }

}
