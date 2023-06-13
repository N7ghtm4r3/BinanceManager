package com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.records.loan.LoanBaseStructure;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records.CryptoLoanBorrowHistory.LoanBorrowHistoryItem;

/**
 * The {@code CryptoLoanBorrowHistory} class is useful to create a crypto loan borrow history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#borrow-get-loan-borrow-history-user_data">
 * Borrow - Get Loan Borrow History (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class CryptoLoanBorrowHistory extends BinanceRowsList<LoanBorrowHistoryItem> {

    /**
     * Constructor to init {@link CryptoLoanBorrowHistory} object
     *
     * @param total   : number of borrows
     * @param borrows :  list of the borrows
     */
    public CryptoLoanBorrowHistory(int total, ArrayList<LoanBorrowHistoryItem> borrows) {
        super(total, borrows);
    }

    /**
     * Constructor to init {@link CryptoLoanBorrowHistory}
     *
     * @param jHistory : history details as {@link JSONObject}
     */
    public CryptoLoanBorrowHistory(JSONObject jHistory) {
        super(jHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new LoanBorrowHistoryItem((JSONObject) row));
    }

    /**
     * The {@code LoanBorrowHistoryItem} class is useful to create a crypto loan borrow history item
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see LoanBaseStructure
     */
    public static class LoanBorrowHistoryItem extends LoanBaseStructure {

        /**
         * {@code BorrowStatus} list of available borrow statuses
         */
        public enum BorrowStatus {

            /**
             * {@code Accruing_Interest} borrow status
             */
            Accruing_Interest,

            /**
             * {@code Overdue} borrow status
             */
            Overdue,

            /**
             * {@code Liquidating} borrow status
             */
            Liquidating,

            /**
             * {@code Repaying} borrow status
             */
            Repaying,

            /**
             * {@code Repaid} borrow status
             */
            Repaid,

            /**
             * {@code Liquidated} borrow status
             */
            Liquidated,

            /**
             * {@code Pending} borrow status
             */
            Pending,

            /**
             * {@code Failed} borrow status
             */
            Failed

        }

        /**
         * {@code orderId} order id of the loan borrow
         */
        private final long orderId;

        /**
         * {@code initialLoanAmount} initial loan amount of the loan borrow
         */
        private final double initialLoanAmount;

        /**
         * {@code hourlyInterestRate} hourly interest rate of the loan borrow
         */
        private final double hourlyInterestRate;

        /**
         * {@code loanTerm} loan term of the loan borrow
         */
        private final int loanTerm;

        /**
         * {@code initialCollateralAmount} initial collateral amount of the loan borrow
         */
        private final double initialCollateralAmount;

        /**
         * {@code borrowTime} borrow time of the loan borrow
         */
        private final long borrowTime;

        /**
         * {@code status} of the loan borrow
         */
        private final BorrowStatus status;

        /**
         * Constructor to init {@link LoanBorrowHistoryItem} object
         *
         * @param loanCoin       :       coin of the loan borrow
         * @param collateralCoin : collateral coin of the loan borrow
         * @param orderId : order id of the loan borrow
         * @param initialLoanAmount       : initial loan amount of the loan borrow
         * @param hourlyInterestRate : hourly interest rate of the loan borrow
         * @param loanTerm       : loan term of the loan borrow
         * @param initialCollateralAmount : initial collateral amount of the loan borrow
         * @param borrowTime       : borrow time of the loan borrow
         * @param status : status of the loan borrow
         */
        public LoanBorrowHistoryItem(String loanCoin, String collateralCoin, long orderId, double initialLoanAmount,
                                     double hourlyInterestRate, int loanTerm, double initialCollateralAmount,
                                     long borrowTime, BorrowStatus status) {
            super(loanCoin, collateralCoin);
            this.orderId = orderId;
            this.initialLoanAmount = initialLoanAmount;
            this.hourlyInterestRate = hourlyInterestRate;
            this.loanTerm = loanTerm;
            this.initialCollateralAmount = initialCollateralAmount;
            this.borrowTime = borrowTime;
            this.status = status;
        }

        /**
         * Constructor to init {@link LoanBorrowHistoryItem} object
         *
         * @param jLoanBorrowHistoryItem : loan borrow history item details as {@link JSONObject}
         */
        public LoanBorrowHistoryItem(JSONObject jLoanBorrowHistoryItem) {
            super(jLoanBorrowHistoryItem);
            orderId = hItem.getLong("orderId", 0);
            initialLoanAmount = hItem.getDouble("initialLoanAmount", 0);
            hourlyInterestRate = hItem.getDouble("hourlyInterestRate", 0);
            loanTerm = hItem.getInt("loanTerm", 0);
            initialCollateralAmount = hItem.getDouble("initialCollateralAmount", 0);
            borrowTime = hItem.getLong("borrowTime", 0);
            status = BorrowStatus.valueOf(hItem.getString("status"));
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
         * Method to get {@link #initialLoanAmount} instance <br>
         * No-any params required
         *
         * @return {@link #initialLoanAmount} instance as double
         */
        public double getInitialLoanAmount() {
            return initialLoanAmount;
        }

        /**
         * Method to get {@link #initialLoanAmount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #initialLoanAmount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getInitialLoanAmount(int decimals) {
            return roundValue(initialLoanAmount, decimals);
        }

        /**
         * Method to get {@link #hourlyInterestRate} instance <br>
         * No-any params required
         *
         * @return {@link #hourlyInterestRate} instance as double
         */
        public double getHourlyInterestRate() {
            return hourlyInterestRate;
        }

        /**
         * Method to get {@link #hourlyInterestRate} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #hourlyInterestRate} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getHourlyInterestRate(int decimals) {
            return roundValue(hourlyInterestRate, decimals);
        }

        /**
         * Method to get {@link #loanTerm} instance <br>
         * No-any params required
         *
         * @return {@link #loanTerm} instance as int
         */
        public int getLoanTerm() {
            return loanTerm;
        }

        /**
         * Method to get {@link #initialCollateralAmount} instance <br>
         * No-any params required
         *
         * @return {@link #initialCollateralAmount} instance as double
         */
        public double getInitialCollateralAmount() {
            return initialCollateralAmount;
        }

        /**
         * Method to get {@link #initialCollateralAmount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #initialCollateralAmount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getInitialCollateralAmount(int decimals) {
            return roundValue(initialCollateralAmount, decimals);
        }

        /**
         * Method to get {@link #borrowTime} instance <br>
         * No-any params required
         *
         * @return {@link #borrowTime} instance as long
         */
        public long getBorrowTime() {
            return borrowTime;
        }

        /**
         * Method to get {@link #borrowTime} instance <br>
         * No-any params required
         *
         * @return {@link #borrowTime} instance as {@link Date}
         */
        public Date getBorrowDate() {
            return TimeFormatter.getDate(borrowTime);
        }

        /**
         * Method to get {@link #status} instance <br>
         * No-any params required
         *
         * @return {@link #status} instance as {@link BorrowStatus}
         */
        public BorrowStatus getStatus() {
            return status;
        }

    }

}
