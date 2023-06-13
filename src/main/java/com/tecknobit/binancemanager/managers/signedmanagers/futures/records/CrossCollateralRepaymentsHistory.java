package com.tecknobit.binancemanager.managers.signedmanagers.futures.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Status;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.futures.records.CrossCollateralRepaymentsHistory.CrossCollateralRepayment;

/**
 * The {@code CrossCollateralRepaymentsHistory} class is useful to create a cross collateral repayment history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-collateral-repayment-history-user_data">
 * Cross-Collateral Repayment History (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class CrossCollateralRepaymentsHistory extends BinanceRowsList<CrossCollateralRepayment> {

    /**
     * Constructor to init {@link CrossCollateralRepaymentsHistory} object
     *
     * @param total      : number of repayments
     * @param repayments :  list of the repayments
     */
    public CrossCollateralRepaymentsHistory(int total, ArrayList<CrossCollateralRepayment> repayments) {
        super(total, repayments);
    }

    /**
     * Constructor to init {@link CrossCollateralRepaymentsHistory}
     *
     * @param jHistory : history details as {@link JSONObject}
     */
    public CrossCollateralRepaymentsHistory(JSONObject jHistory) {
        super(jHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new CrossCollateralRepayment((JSONObject) row));
    }

    /**
     * The {@code CrossCollateralRepayment} class is useful to create a cross collateral repayment
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see CrossCollateralItem
     */
    public static class CrossCollateralRepayment extends CrossCollateralItem {

        /**
         * {@code RepayType} list of available repay types
         */
        public enum RepayType {

            /**
             * {@code NORMAL} repay type
             */
            NORMAL,

            /**
             * {@code COLLATERAL} repay type
             */
            COLLATERAL

        }

        /**
         * {@code amount} of the repayment
         */
        private final double amount;

        /**
         * {@code type} of the repayment
         */
        private final RepayType repayType;

        /**
         * {@code releasedCollateral} released collateral of the repayment
         */
        private final double releasedCollateral;

        /**
         * {@code price} of the repayment
         */
        private final double price;

        /**
         * {@code repayCollateral} repay collateral of the repayment
         */
        private final double repayCollateral;

        /**
         * {@code confirmedTime} confirmed time of the repayment
         */
        private final long confirmedTime;

        /**
         * {@code updateTime} update time of the repayment
         */
        private final long updateTime;

        /**
         * {@code status} of the repayment
         */
        private final Status status;

        /**
         * {@code repayId} id of the repayment
         */
        private final long repayId;

        /**
         * Constructor to init {@link CrossCollateralRepayment} object
         *
         * @param coin:               coin of the cross collateral
         * @param collateralCoin:     collateral coin of the cross collateral
         * @param amount:             amount of the repayment
         * @param repayType:          type of the repayment
         * @param releasedCollateral: repay collateral of the repayment
         * @param price:              price of the repayment
         * @param repayCollateral:    repay collateral of the repayment
         * @param confirmedTime:      confirmed time of the repayment
         * @param updateTime:         update time of the repayment
         * @param status:             status of the repayment
         * @param repayId:            id of the repayment
         */
        public CrossCollateralRepayment(String coin, String collateralCoin, double amount, RepayType repayType,
                                        double releasedCollateral, double price, double repayCollateral,
                                        long confirmedTime, long updateTime, Status status, long repayId) {
            super(coin, collateralCoin);
            this.amount = amount;
            this.repayType = repayType;
            this.releasedCollateral = releasedCollateral;
            this.price = price;
            this.repayCollateral = repayCollateral;
            this.confirmedTime = confirmedTime;
            this.updateTime = updateTime;
            this.status = status;
            this.repayId = repayId;
        }

        /**
         * Constructor to init {@link CrossCollateralRepayment} object
         *
         * @param jCrossCollateralRepayment: cross collateral repayment details as {@link JSONObject}
         */
        public CrossCollateralRepayment(JSONObject jCrossCollateralRepayment) {
            super(jCrossCollateralRepayment);
            amount = hItem.getDouble("amount", 0);
            repayType = RepayType.valueOf(hItem.getString("repayType"));
            releasedCollateral = hItem.getDouble("releasedCollateral", 0);
            price = hItem.getDouble("price", 0);
            repayCollateral = hItem.getDouble("repayCollateral", 0);
            confirmedTime = hItem.getLong("confirmedTime", 0);
            updateTime = hItem.getLong("updateTime", 0);
            status = Status.valueOf(hItem.getString("status"));
            repayId = hItem.getLong("repayId", 0);
        }

        /**
         * Method to get {@link #amount} instance <br>
         * No-any params required
         *
         * @return {@link #amount} instance as double
         */
        public double getAmount() {
            return amount;
        }

        /**
         * Method to get {@link #amount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #amount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getAmount(int decimals) {
            return roundValue(amount, decimals);
        }

        /**
         * Method to get {@link #repayType} instance <br>
         * No-any params required
         *
         * @return {@link #repayType} instance as {@link RepayType}
         */
        public RepayType getRepayType() {
            return repayType;
        }

        /**
         * Method to get {@link #releasedCollateral} instance <br>
         * No-any params required
         *
         * @return {@link #releasedCollateral} instance as double
         */
        public double getReleasedCollateral() {
            return releasedCollateral;
        }

        /**
         * Method to get {@link #releasedCollateral} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #releasedCollateral} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getReleasedCollateral(int decimals) {
            return roundValue(releasedCollateral, decimals);
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
         * Method to get {@link #repayCollateral} instance <br>
         * No-any params required
         *
         * @return {@link #repayCollateral} instance as double
         */
        public double getRepayCollateral() {
            return repayCollateral;
        }

        /**
         * Method to get {@link #repayCollateral} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #repayCollateral} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getRepayCollateral(int decimals) {
            return roundValue(repayCollateral, decimals);
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
        public Date getConfirmDate() {
            return TimeFormatter.getDate(confirmedTime);
        }

        /**
         * Method to get {@link #updateTime} instance <br>
         * No-any params required
         *
         * @return {@link #updateTime} instance as long
         */
        public long getUpdateTime() {
            return updateTime;
        }

        /**
         * Method to get {@link #updateTime} instance <br>
         * No-any params required
         *
         * @return {@link #updateTime} instance as {@link Date}
         */
        public Date getUpdateDate() {
            return TimeFormatter.getDate(updateTime);
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
         * Method to get {@link #repayId} instance <br>
         * No-any params required
         *
         * @return {@link #repayId} instance as long
         */
        public long getRepayId() {
            return repayId;
        }

    }

}
