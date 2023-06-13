package com.tecknobit.binancemanager.managers.signedmanagers.futures.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Status;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.futures.records.CrossCollateralBorrowHistory.CrossCollateralBorrow;

/**
 * The {@code CrossCollateralBorrowHistory} class is useful to create a cross collateral borrow history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-collateral-borrow-history-user_data">
 * Cross-Collateral Borrow History (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class CrossCollateralBorrowHistory extends BinanceRowsList<CrossCollateralBorrow> {

    /**
     * Constructor to init {@link CrossCollateralBorrowHistory} object
     *
     * @param total   : number of borrows
     * @param borrows :  list of the borrows
     */
    public CrossCollateralBorrowHistory(int total, ArrayList<CrossCollateralBorrow> borrows) {
        super(total, borrows);
    }

    /**
     * Constructor to init {@link CrossCollateralBorrowHistory}
     *
     * @param jHistory : history details as {@link JSONObject}
     */
    public CrossCollateralBorrowHistory(JSONObject jHistory) {
        super(jHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new CrossCollateralBorrow((JSONObject) row));
    }

    /**
     * The {@code CrossCollateralBorrow} class is useful to create a cross collateral borrow
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see CrossCollateralItem
     */
    public static class CrossCollateralBorrow extends CrossCollateralItem {

        /**
         * {@code confirmedTime} confirmed time of the cross collateral borrow
         */
        private final long confirmedTime;

        /**
         * {@code collateralRate} collateral rate of the cross collateral borrow
         */
        private final double collateralRate;

        /**
         * {@code leftTotal} left total of the cross collateral borrow
         */
        private final double leftTotal;

        /**
         * {@code leftPrincipal} left principal of the cross collateral borrow
         */
        private final double leftPrincipal;

        /**
         * {@code deadline} of the cross collateral borrow
         */
        private final long deadline;

        /**
         * {@code collateralAmount} collateral amount of the cross collateral borrow
         */
        private final double collateralAmount;

        /**
         * {@code orderStatus} order status of the cross collateral borrow
         */
        private final Status orderStatus;

        /**
         * {@code borrowId} borrow id of the cross collateral borrow
         */
        private final long borrowId;

        /**
         * Constructor to init {@link CrossCollateralBorrow} object
         *
         * @param coin:             coin of the cross collateral
         * @param collateralCoin:   collateral coin of the cross collateral
         * @param confirmedTime:    confirmed time of the cross collateral borrow
         * @param collateralRate:   collateral rate of the cross collateral borrow
         * @param leftPrincipal:    left principal of the cross collateral borrow
         * @param deadline:         deadline of the cross collateral borrow
         * @param collateralAmount: collateral amount of the cross collateral borrow
         * @param orderStatus:      order status of the cross collateral borrow
         * @param borrowId:         borrow id of the cross collateral borrow
         */
        public CrossCollateralBorrow(String coin, String collateralCoin, long confirmedTime, double collateralRate,
                                     double leftTotal, double leftPrincipal, long deadline, double collateralAmount,
                                     Status orderStatus, long borrowId) {
            super(coin, collateralCoin);
            this.confirmedTime = confirmedTime;
            this.collateralRate = collateralRate;
            this.leftTotal = leftTotal;
            this.leftPrincipal = leftPrincipal;
            this.deadline = deadline;
            this.collateralAmount = collateralAmount;
            this.orderStatus = orderStatus;
            this.borrowId = borrowId;
        }

        /**
         * Constructor to init {@link CrossCollateralBorrow} object
         *
         * @param jCrossCollateralBorrow: cross collateral borrow details as {@link JSONObject}
         */
        public CrossCollateralBorrow(JSONObject jCrossCollateralBorrow) {
            super(jCrossCollateralBorrow);
            confirmedTime = hItem.getLong("confirmedTime", 0);
            collateralRate = hItem.getDouble("collateralRate", 0);
            leftTotal = hItem.getDouble("leftTotal", 0);
            leftPrincipal = hItem.getDouble("leftPrincipal", 0);
            deadline = hItem.getLong("deadline", 0);
            collateralAmount = hItem.getDouble("collateralAmount", 0);
            orderStatus = Status.valueOf(hItem.getString("orderStatus"));
            borrowId = hItem.getLong("borrowId", 0);
        }

        /**
         * Method to get {@link #confirmedTime} instance <br>
         * No-any params required
         *
         * @return {@link #confirmedTime} instance as long
         */
        public long getConfirmedTime() {
            return confirmedTime;
        }

        /**
         * Method to get {@link #confirmedTime} instance <br>
         * No-any params required
         *
         * @return {@link #confirmedTime} instance as {@link Date}
         */
        public Date getConfirmedDate() {
            return TimeFormatter.getDate(confirmedTime);
        }

        /**
         * Method to get {@link #collateralRate} instance <br>
         * No-any params required
         *
         * @return {@link #collateralRate} instance as double
         */
        public double getCollateralRate() {
            return collateralRate;
        }

        /**
         * Method to get {@link #collateralRate} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #collateralRate} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getCollateralRate(int decimals) {
            return roundValue(collateralRate, decimals);
        }

        /**
         * Method to get {@link #leftTotal} instance <br>
         * No-any params required
         *
         * @return {@link #leftTotal} instance as double
         */
        public double getLeftTotal() {
            return leftTotal;
        }

        /**
         * Method to get {@link #leftTotal} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #leftTotal} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getLeftTotal(int decimals) {
            return roundValue(leftTotal, decimals);
        }

        /**
         * Method to get {@link #leftPrincipal} instance <br>
         * No-any params required
         *
         * @return {@link #leftPrincipal} instance as double
         */
        public double getLeftPrincipal() {
            return leftPrincipal;
        }

        /**
         * Method to get {@link #leftPrincipal} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #leftPrincipal} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getLeftPrincipal(int decimals) {
            return roundValue(leftPrincipal, decimals);
        }

        /**
         * Method to get {@link #deadline} instance <br>
         * No-any params required
         *
         * @return {@link #deadline} instance as long
         */
        public long getDeadline() {
            return deadline;
        }

        /**
         * Method to get {@link #deadline} instance <br>
         * No-any params required
         *
         * @return {@link #deadline} instance as {@link Date}
         */
        public Date getDeadlineDate() {
            return TimeFormatter.getDate(deadline);
        }

        /**
         * Method to get {@link #collateralAmount} instance <br>
         * No-any params required
         *
         * @return {@link #collateralAmount} instance as double
         */
        public double getCollateralAmount() {
            return collateralAmount;
        }

        /**
         * Method to get {@link #collateralAmount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #collateralAmount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getCollateralAmount(int decimals) {
            return roundValue(collateralAmount, decimals);
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
         * Method to get {@link #borrowId} instance <br>
         * No-any params required
         *
         * @return {@link #borrowId} instance as long
         */
        public long getBorrowId() {
            return borrowId;
        }

    }

}


