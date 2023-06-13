package com.tecknobit.binancemanager.managers.signedmanagers.viploans.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.records.loan.LoanBaseRepayStructure;
import com.tecknobit.binancemanager.managers.records.loan.LoanBaseStructure;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.viploans.records.VIPLoanRepaymentHistory.VIPLoanRepayment;

/**
 * The {@code VIPLoanRepaymentHistory} class is useful to create a VIP loan repayment history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-vip-loan-repayment-history-user_data">
 * Get VIP Loan Repayment History (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class VIPLoanRepaymentHistory extends BinanceRowsList<VIPLoanRepayment> {

    /**
     * Constructor to init {@link VIPLoanRepaymentHistory} object
     *
     * @param total      : number of repayments
     * @param repayments :  list of the repayments
     */
    public VIPLoanRepaymentHistory(int total, ArrayList<VIPLoanRepayment> repayments) {
        super(total, repayments);
    }

    /**
     * Constructor to init {@link VIPLoanRepaymentHistory}
     *
     * @param jHistory : history details as {@link JSONObject}
     */
    public VIPLoanRepaymentHistory(JSONObject jHistory) {
        super(jHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new VIPLoanRepayment((JSONObject) row));
    }

    /**
     * The {@code VIPLoanRepayment} class is useful to create a VIP loan repayment
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see LoanBaseStructure
     */
    public static class VIPLoanRepayment extends LoanBaseRepayStructure {

        /**
         * {@code repayTime} repay time of the VIP loan repayment
         */
        protected final long repayTime;

        /**
         * {@code orderId} order id of the VIP loan repayment
         */
        protected final long orderId;

        /**
         * {@code repayAmount} repay amount of the loan
         */
        protected final double repayAmount;

        /**
         * Constructor to init {@link VIPLoanRepayment} object
         *
         * @param loanCoin:       coin of the vip loan
         * @param collateralCoin: collateral coin of the vip loan
         * @param repayAmount:    repay amount of the loan
         * @param repayStatus:    repay status of the loan
         * @param repayTime:      repay time of the VIP loan repayment
         * @param orderId:        order id of the VIP loan repayment
         */
        public VIPLoanRepayment(String loanCoin, String collateralCoin, RepayStatus repayStatus, long repayTime,
                                long orderId, double repayAmount) {
            super(loanCoin, collateralCoin, repayStatus);
            this.repayTime = repayTime;
            this.orderId = orderId;
            this.repayAmount = repayAmount;
        }

        /**
         * Constructor to init {@link VIPLoanRepayment} object
         *
         * @param jVIPLoanRepayment: VIP loan repayment  details as {@link JSONObject}
         */
        public VIPLoanRepayment(JSONObject jVIPLoanRepayment) {
            super(jVIPLoanRepayment);
            repayTime = hItem.getLong("repayTime", 0);
            orderId = hItem.getLong("orderId", 0);
            repayAmount = hItem.getDouble("repayAmount", 0);
        }

        /**
         * Method to get {@link #repayTime} instance <br>
         * No-any params required
         *
         * @return {@link #repayTime} instance as long
         */
        public long getRepayTime() {
            return repayTime;
        }

        /**
         * Method to get {@link #repayTime} instance <br>
         * No-any params required
         *
         * @return {@link #repayTime} instance as {@link Date}
         */
        public Date getRepayDate() {
            return TimeFormatter.getDate(repayTime);
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
         * Method to get {@link #repayAmount} instance <br>
         * No-any params required
         *
         * @return {@link #repayAmount} instance as double
         */
        public double getRepayAmount() {
            return repayAmount;
        }

        /**
         * Method to get {@link #repayAmount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #repayAmount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getRepayAmount(int decimals) {
            return roundValue(repayAmount, decimals);
        }

    }

}
