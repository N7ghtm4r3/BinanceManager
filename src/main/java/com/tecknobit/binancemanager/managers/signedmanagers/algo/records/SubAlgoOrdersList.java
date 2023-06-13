package com.tecknobit.binancemanager.managers.signedmanagers.algo.records;

import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Status;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.TimeInForce;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat;
import static com.tecknobit.binancemanager.managers.signedmanagers.algo.records.SubAlgoOrdersList.SubAlgoOrder;

/**
 * The {@code SubAlgoOrdersList} class is useful to create a sub algo orders list
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-orders-user_data">
 *             Query Sub Orders (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-orders-user_data-2">
 *             Query Sub Orders (USER_DATA)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class SubAlgoOrdersList extends BinanceRowsList<SubAlgoOrder> {

    /**
     * {@code executedQty} executed quantity of the orders list
     */
    private final double executedQty;

    /**
     * {@code executedAmt} executed amount of the orders list
     */
    private final double executedAmt;

    /**
     * Constructor to init {@link SubAlgoOrdersList} object
     *
     * @param total     : number of orders
     * @param subOrders :  list of the orders
     * @param executedQty     : executed quantity of the orders list
     * @param executedAmt :  executed amount of the orders list
     */
    public SubAlgoOrdersList(int total, ArrayList<SubAlgoOrder> subOrders, double executedQty, double executedAmt) {
        super(total, subOrders);
        this.executedQty = executedQty;
        this.executedAmt = executedAmt;
    }

    /**
     * Constructor to init {@link SubAlgoOrdersList}
     *
     * @param jSubAlgoOrdersList : orders list details as {@link JSONObject}
     */
    public SubAlgoOrdersList(JSONObject jSubAlgoOrdersList) {
        super(jSubAlgoOrdersList);
        executedQty = hItem.getDouble("executedQty", 0);
        executedAmt = hItem.getDouble("executedAmt", 0);
        for (Object row : hItem.fetchList("subOrders"))
            rows.add(new SubAlgoOrder((JSONObject) row));
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
        return roundValue(executedQty, decimals);
    }

    /**
     * Method to get {@link #executedAmt} instance <br>
     * No-any params required
     *
     * @return {@link #executedAmt} instance as double
     */
    public double getExecutedAmt() {
        return executedAmt;
    }

    /**
     * Method to get {@link #executedAmt} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #executedAmt} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getExecutedAmt(int decimals) {
        return roundValue(executedAmt, decimals);
    }

    /**
     * Method to create a sub algo orders list
     *
     * @param algoOrdersResponse: obtained from Binance's response
     * @param format:             return type formatter -> {@link ReturnFormat}
     * @return sub algo orders list as {@code "format"} defines
     */
    @Returner
    public static <T> T returnSubAlgoOrders(String algoOrdersResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(algoOrdersResponse);
            case LIBRARY_OBJECT:
                return (T) new SubAlgoOrdersList(new JSONObject(algoOrdersResponse));
            default:
                return (T) algoOrdersResponse;
        }
    }

    /**
     * The {@code SubAlgoOrder} class is useful to create a sub algo order
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see AlgoOrderStructure
     */
    public static class SubAlgoOrder extends AlgoOrderStructure {

        /**
         * {@code orderId} id of the sub algo order
         */
        private final long orderId;

        /**
         * {@code orderStatus} status of the sub algo order
         */
        private final Status orderStatus;

        /**
         * {@code feeAmt} fee amount of the sub algo order
         */
        private final double feeAmt;

        /**
         * {@code feeAsset} fee asset of the sub algo order
         */
        private final String feeAsset;

        /**
         * {@code subId} sub id of the sub algo order
         */
        private final long subId;

        /**
         * {@code timeInForce} time in force of the sub algo order
         */
        private final TimeInForce timeInForce;

        /**
         * {@code origQty} origin quantity of the sub algo order
         */
        private final double origQty;

        /**
         * Constructor to init {@link SubAlgoOrder}
         *
         * @param algoId      : id of the sub algo order
         * @param symbol      : symbol of the sub algo order
         * @param side        : side of the sub algo order
         * @param executedQty : executed quantity of the sub algo order
         * @param executedAmt : executed amount of the sub algo order
         * @param avgPrice    : average price of the sub algo order
         * @param bookTime    : book time of the sub algo order
         * @param orderId     : id of the sub algo order
         * @param orderStatus : status of the sub algo order
         * @param feeAmt      : fee amount of the sub algo order
         * @param feeAsset    : fee asset of the sub algo order
         * @param subId       : sub id of the sub algo order
         * @param timeInForce : time in force of the sub algo order
         * @param origQty     : origin quantity of the sub algo order
         */
        public SubAlgoOrder(long algoId, String symbol, Order.Side side, double executedQty, double executedAmt,
                            double avgPrice, long bookTime, long orderId, Status orderStatus, double feeAmt,
                            String feeAsset, long subId, TimeInForce timeInForce, double origQty) {
            super(algoId, symbol, side, executedQty, executedAmt, avgPrice, bookTime);
            this.orderId = orderId;
            this.orderStatus = orderStatus;
            this.feeAmt = feeAmt;
            this.feeAsset = feeAsset;
            this.subId = subId;
            this.timeInForce = timeInForce;
            this.origQty = origQty;
        }

        /**
         * Constructor to init {@link SubAlgoOrder}
         *
         * @param jSubAlgoOrder : sub algo order details as {@link JSONObject}
         */
        public SubAlgoOrder(JSONObject jSubAlgoOrder) {
            super(jSubAlgoOrder);
            orderId = hItem.getLong("orderId", 0);
            orderStatus = Status.valueOf(hItem.getString("orderStatus"));
            feeAmt = hItem.getDouble("feeAmt", 0);
            feeAsset = hItem.getString("feeAsset");
            subId = hItem.getLong("subId", 0);
            timeInForce = TimeInForce.valueOf(hItem.getString("timeInForce"));
            origQty = hItem.getDouble("origQty", 0);
        }

        /**
         * Method to get {@link #orderId} instance <br>
         * No-any params required
         *
         * @return {@link #orderId} instance as long
         */
        public long getOrderId() {
            return orderId;
        }

        /**
         * Method to get {@link #orderStatus} instance <br>
         * No-any params required
         *
         * @return {@link #orderStatus} instance as {@link Status}
         */
        public Status getOrderStatus() {
            return orderStatus;
        }

        /**
         * Method to get {@link #feeAmt} instance <br>
         * No-any params required
         *
         * @return {@link #feeAmt} instance as double
         */
        public double getFeeAmt() {
            return feeAmt;
        }

        /**
         * Method to get {@link #feeAmt} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #feeAmt} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getFeeAmt(int decimals) {
            return roundValue(feeAmt, decimals);
        }

        /**
         * Method to get {@link #feeAsset} instance <br>
         * No-any params required
         *
         * @return {@link #feeAsset} instance as {@link String}
         */
        public String getFeeAsset() {
            return feeAsset;
        }

        /**
         * Method to get {@link #subId} instance <br>
         * No-any params required
         *
         * @return {@link #subId} instance as long
         */
        public long getSubId() {
            return subId;
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

    }

}
