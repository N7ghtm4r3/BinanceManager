package com.tecknobit.binancemanager.managers.signedmanagers.futuresalgo.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Side;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.futuresalgo.records.AlgoOrdersList.AlgoOrder;

/**
 * The {@code AlgoOrdersList} class is useful to create an algo orders list
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#query-current-algo-open-orders-user_data">
 *             Query Current Algo Open Orders (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#query-historical-algo-orders-user_data">
 *             Query Historical Algo Orders (USER_DATA)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 * @see BinanceRowsList
 **/
public class AlgoOrdersList extends BinanceRowsList<AlgoOrder> {

    /**
     * Constructor to init {@link AlgoOrdersList} object
     *
     * @param total  : number of orders
     * @param orders :  list of the orders
     **/
    public AlgoOrdersList(int total, ArrayList<AlgoOrder> orders) {
        super(total, orders);
    }

    /**
     * Constructor to init {@link AlgoOrdersList}
     *
     * @param jAlgoOrdersList : orders list details as {@link JSONObject}
     **/
    public AlgoOrdersList(JSONObject jAlgoOrdersList) {
        super(jAlgoOrdersList);
        for (Object row : hItem.fetchList("orders"))
            rows.add(new AlgoOrder((JSONObject) row));
    }

    /**
     * The {@code AlgoOrder} class is useful to create an algo order
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see AlgoOrderStructure
     **/
    public static class AlgoOrder extends AlgoOrderStructure {

        /**
         * {@code PositionSide} list of available position sides
         **/
        public enum PositionSide {

            /**
             * {@code BOTH} position side
             **/
            BOTH,

            /**
             * {@code LONG} position side
             **/
            LONG,

            /**
             * {@code SHORT} position side
             **/
            SHORT

        }

        /**
         * {@code AlgoStatus} list of available algo statuses
         **/
        public enum AlgoStatus {

            /**
             * {@code WORKING} algo status
             **/
            WORKING,

            /**
             * {@code CANCELLED} algo status
             **/
            CANCELLED

        }

        /**
         * {@code Urgency} list of available urgencies
         **/
        public enum Urgency {

            /**
             * {@code LOW} urgency
             **/
            LOW,

            /**
             * {@code MEDIUM} urgency
             **/
            MEDIUM,

            /**
             * {@code HIGH} urgency
             **/
            HIGH

        }

        /**
         * {@code AlgoType} list of available algo types
         **/
        public enum AlgoType {

            /**
             * {@code VP} algo type
             **/
            VP,

            /**
             * {@code Twap} algo type
             **/
            Twap

        }

        /**
         * {@code positionSide} position side of the algo order
         **/
        private final PositionSide positionSide;

        /**
         * {@code totalQty} total quantity of the algo order
         **/
        private final double totalQty;

        /**
         * {@code clientAlgoId} client algo identifier of the algo order
         **/
        private final String clientAlgoId;

        /**
         * {@code endTime} end time of the algo order
         **/
        private final long endTime;

        /**
         * {@code algoStatus} status of the algo order
         **/
        private final AlgoStatus algoStatus;

        /**
         * {@code algoType} type of the algo order
         **/
        private final AlgoType algoType;

        /**
         * {@code urgency} of the algo order
         **/
        private final Urgency urgency;

        /**
         * Constructor to init {@link AlgoOrder}
         *
         * @param algoId       : id of the algo order
         * @param symbol       : symbol of the algo order
         * @param side         : side of the algo order
         * @param executedQty  : executed quantity of the algo order
         * @param executedAmt  : executed amount of the algo order
         * @param avgPrice     : average price of the algo order
         * @param bookTime     : book time of the algo order
         * @param positionSide : position side of the algo order
         * @param totalQty     : total quantity of the algo order
         * @param clientAlgoId : client algo identifier of the algo order
         * @param endTime      : end time of the algo order
         * @param algoStatus   : status of the algo order
         * @param algoType     : type of the algo order
         * @param urgency      : urgency of the algo order
         **/
        public AlgoOrder(long algoId, String symbol, Side side, double executedQty, double executedAmt, double avgPrice,
                         long bookTime, PositionSide positionSide, double totalQty, String clientAlgoId, long endTime,
                         AlgoStatus algoStatus, AlgoType algoType, Urgency urgency) {
            super(algoId, symbol, side, executedQty, executedAmt, avgPrice, bookTime);
            this.positionSide = positionSide;
            this.totalQty = totalQty;
            this.clientAlgoId = clientAlgoId;
            this.endTime = endTime;
            this.algoStatus = algoStatus;
            this.algoType = algoType;
            this.urgency = urgency;
        }

        /**
         * Constructor to init {@link AlgoOrder}
         *
         * @param jAlgoOrder : algo order details as {@link JSONObject}
         **/
        public AlgoOrder(JSONObject jAlgoOrder) {
            super(jAlgoOrder);
            positionSide = PositionSide.valueOf(hItem.getString("positionSide"));
            totalQty = hItem.getDouble("totalQty", 0);
            clientAlgoId = hItem.getString("clientAlgoId");
            endTime = hItem.getLong("endTime", 0);
            algoStatus = AlgoStatus.valueOf(hItem.getString("algoStatus"));
            algoType = AlgoType.valueOf(hItem.getString("algoType"));
            urgency = Urgency.valueOf(hItem.getString("urgency"));
        }

        /**
         * Method to get {@link #positionSide} instance <br>
         * No-any params required
         *
         * @return {@link #positionSide} instance as {@link PositionSide}
         **/
        public PositionSide getPositionSide() {
            return positionSide;
        }

        /**
         * Method to get {@link #totalQty} instance <br>
         * No-any params required
         *
         * @return {@link #totalQty} instance as double
         **/
        public double getTotalQty() {
            return totalQty;
        }

        /**
         * Method to get {@link #totalQty} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #totalQty} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getTotalQty(int decimals) {
            return roundValue(totalQty, decimals);
        }

        /**
         * Method to get {@link #clientAlgoId} instance <br>
         * No-any params required
         *
         * @return {@link #clientAlgoId} instance as {@link String}
         **/
        public String getClientAlgoId() {
            return clientAlgoId;
        }

        /**
         * Method to get {@link #algoId} instance <br>
         * No-any params required
         *
         * @return {@link #algoId} instance as long
         **/
        public long getEndTime() {
            return endTime;
        }

        /**
         * Method to get {@link #endTime} instance <br>
         * No-any params required
         *
         * @return {@link #endTime} instance as {@link Date}
         **/
        public Date getEndDate() {
            return TimeFormatter.getDate(endTime);
        }

        /**
         * Method to get {@link #algoStatus} instance <br>
         * No-any params required
         *
         * @return {@link #algoStatus} instance as {@link AlgoStatus}
         **/
        public AlgoStatus getAlgoStatus() {
            return algoStatus;
        }

        /**
         * Method to get {@link #algoType} instance <br>
         * No-any params required
         *
         * @return {@link #algoType} instance as {@link AlgoType}
         **/
        public AlgoType getAlgoType() {
            return algoType;
        }

        /**
         * Method to get {@link #urgency} instance <br>
         * No-any params required
         *
         * @return {@link #urgency} instance as {@link Urgency}
         **/
        public Urgency getUrgency() {
            return urgency;
        }

    }

}
