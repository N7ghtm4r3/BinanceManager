package com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.records.loan.LoanBaseStructure;
import com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records.CryptoLoanAdjustLTV.LoanAdjustDirection;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records.LoanLTVAdjustmentHistory.LoanLTVAdjustment;

/**
 * The {@code LoanLTVAdjustmentHistory} class is useful to create a crypto loan LTV adjustment history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#adjust-ltv-get-loan-ltv-adjustment-history-user_data">
 * Adjust LTV - Get Loan LTV Adjustment History (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 **/
public class LoanLTVAdjustmentHistory extends BinanceRowsList<LoanLTVAdjustment> {

    /**
     * Constructor to init {@link LoanLTVAdjustmentHistory} object
     *
     * @param total       : number of adjustments
     * @param adjustments :  list of the adjustments
     **/
    public LoanLTVAdjustmentHistory(int total, ArrayList<LoanLTVAdjustment> adjustments) {
        super(total, adjustments);
    }

    /**
     * Constructor to init {@link LoanLTVAdjustmentHistory}
     *
     * @param jHistory : history details as {@link JSONObject}
     **/
    public LoanLTVAdjustmentHistory(JSONObject jHistory) {
        super(jHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new LoanLTVAdjustment((JSONObject) row));
    }

    /**
     * The {@code LoanLTVAdjustment} class is useful to create a crypto loan LTV adjustment
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see LoanBaseStructure
     **/
    public static class LoanLTVAdjustment extends LoanBaseStructure {

        /**
         * {@code direction} of the loan LTV adjustment
         **/
        private final LoanAdjustDirection direction;

        /**
         * {@code amount} of the loan LTV adjustment
         **/
        private final double amount;

        /**
         * {@code preLTV} pre LTV of the loan LTV adjustment
         **/
        private final double preLTV;

        /**
         * {@code afterLTV} after LTV of the loan LTV adjustment
         **/
        private final double afterLTV;

        /**
         * {@code adjustTime} adjust time of the loan LTV adjustment
         **/
        private final long adjustTime;

        /**
         * {@code orderId} order id of the loan LTV adjustment
         **/
        private final long orderId;

        /**
         * Constructor to init {@link LoanLTVAdjustment} object
         *
         * @param loanCoin       : coin of the loan LTV adjustment
         * @param collateralCoin : collateral coin of the loan LTV adjustment
         * @param direction       : direction of the loan LTV adjustment
         * @param amount : amount LTV of the loan LTV adjustment
         * @param preLTV       : pre LTV of the loan LTV adjustment
         * @param afterLTV : after LTV of the loan LTV adjustment
         * @param adjustTime       : adjust time of the loan LTV adjustment
         * @param orderId : order id of the loan LTV adjustment
         **/
        public LoanLTVAdjustment(String loanCoin, String collateralCoin, LoanAdjustDirection direction, double amount,
                                 double preLTV, double afterLTV, long adjustTime, long orderId) {
            super(loanCoin, collateralCoin);
            this.direction = direction;
            this.amount = amount;
            this.preLTV = preLTV;
            this.afterLTV = afterLTV;
            this.adjustTime = adjustTime;
            this.orderId = orderId;
        }

        /**
         * Constructor to init {@link LoanLTVAdjustment} object
         *
         * @param jLoanLTVAdjustment : loan LTV adjustment details as {@link JSONObject}
         **/
        public LoanLTVAdjustment(JSONObject jLoanLTVAdjustment) {
            super(jLoanLTVAdjustment);
            direction = LoanAdjustDirection.valueOf(hItem.getString("direction"));
            amount = hItem.getDouble("amount", 0);
            preLTV = hItem.getDouble("preLTV", 0);
            afterLTV = hItem.getDouble("afterLTV", 0);
            adjustTime = hItem.getLong("adjustTime", 0);
            orderId = hItem.getLong("orderId", 0);
        }

        /**
         * Method to get {@link #direction} instance <br>
         * No-any params required
         *
         * @return {@link #direction} instance as {@link LoanAdjustDirection}
         **/
        public LoanAdjustDirection getDirection() {
            return direction;
        }

        /**
         * Method to get {@link #amount} instance <br>
         * No-any params required
         *
         * @return {@link #amount} instance as double
         **/
        public double getAmount() {
            return amount;
        }

        /**
         * Method to get {@link #amount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #amount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getAmount(int decimals) {
            return roundValue(amount, decimals);
        }

        /**
         * Method to get {@link #preLTV} instance <br>
         * No-any params required
         *
         * @return {@link #preLTV} instance as double
         **/
        public double getPreLTV() {
            return preLTV;
        }

        /**
         * Method to get {@link #preLTV} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #preLTV} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getPreLTV(int decimals) {
            return roundValue(preLTV, decimals);
        }

        /**
         * Method to get {@link #afterLTV} instance <br>
         * No-any params required
         *
         * @return {@link #afterLTV} instance as double
         **/
        public double getAfterLTV() {
            return afterLTV;
        }

        /**
         * Method to get {@link #afterLTV} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #afterLTV} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getAfterLTV(int decimals) {
            return roundValue(afterLTV, decimals);
        }

        /**
         * Method to get {@link #adjustTime} instance <br>
         * No-any params required
         *
         * @return {@link #adjustTime} instance as long
         **/
        public long getAdjustTime() {
            return adjustTime;
        }

        /**
         * Method to get {@link #adjustTime} instance <br>
         * No-any params required
         *
         * @return {@link #adjustTime} instance as {@link Date}
         **/
        public Date getAdjustDate() {
            return TimeFormatter.getDate(adjustTime);
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

    }

}
