package com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.records.loan.LoanBaseRepayStructure;
import com.tecknobit.binancemanager.managers.records.loan.LoanBaseStructure;
import com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records.CryptoLoanRepay.LoanRepayType;
import com.tecknobit.binancemanager.managers.signedmanagers.viploans.records.VIPLoanRepaymentHistory.VIPLoanRepayment;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records.CryptoLoanRepaymentHistory.CryptoLoanRepayment;

/**
 * The {@code CryptoLoanRepaymentHistory} class is useful to create a crypto loan repayment history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#repay-get-loan-repayment-history-user_data">
 * Repay - Get Loan Repayment History (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class CryptoLoanRepaymentHistory extends BinanceRowsList<CryptoLoanRepayment> {

    /**
     * Constructor to init {@link CryptoLoanRepaymentHistory} object
     *
     * @param total      : number of repayments
     * @param repayments :  list of the repayments
     */
    public CryptoLoanRepaymentHistory(int total, ArrayList<CryptoLoanRepayment> repayments) {
        super(total, repayments);
    }

    /**
     * Constructor to init {@link CryptoLoanRepaymentHistory}
     *
     * @param jHistory : history details as {@link JSONObject}
     */
    public CryptoLoanRepaymentHistory(JSONObject jHistory) {
        super(jHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new CryptoLoanRepayment((JSONObject) row));
    }

    /**
     * The {@code CryptoLoanRepayment} class is useful to create a crypto loan repayment
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see LoanBaseStructure
     * @see LoanBaseRepayStructure
     * @see VIPLoanRepayment
     */
    public static class CryptoLoanRepayment extends VIPLoanRepayment {

        /**
         * {@code collateralUsed} collateral used for the crypto loan repayment
         */
        private final double collateralUsed;

        /**
         * {@code collateralReturn} collateral return of the crypto loan repayment
         */
        private final double collateralReturn;

        /**
         * {@code repayType} repay type of the crypto loan repayment
         */
        private final LoanRepayType repayType;

        /**
         * Constructor to init {@link CryptoLoanRepayment} object
         *
         * @param loanCoin         :       coin of the crypto loan repayment
         * @param collateralCoin   : collateral coin of the crypto loan repayment
         * @param repayStatus      :    repay status of the loan
         * @param repayTime        :      repay time of the crypto loan repayment
         * @param orderId          :        order id of the crypto loan repayment
         * @param repayAmount      :    repay amount of the crypto loan repayment
         * @param collateralUsed   : collateral used for the crypto loan repayment
         * @param collateralReturn : collateral return of the crypto loan repayment
         * @param repayType        : repay type of the crypto loan repayment
         */
        public CryptoLoanRepayment(String loanCoin, String collateralCoin, RepayStatus repayStatus, long repayTime,
                                   long orderId, double repayAmount, double collateralUsed, double collateralReturn,
                                   LoanRepayType repayType) {
            super(loanCoin, collateralCoin, repayStatus, repayTime, orderId, repayAmount);
            this.collateralUsed = collateralUsed;
            this.collateralReturn = collateralReturn;
            this.repayType = repayType;
        }

        /**
         * Constructor to init {@link CryptoLoanRepayment} object
         *
         * @param jCryptoLoanRepayment : loan repayment details as {@link JSONObject}
         */
        public CryptoLoanRepayment(JSONObject jCryptoLoanRepayment) {
            super(jCryptoLoanRepayment);
            collateralUsed = hItem.getDouble("collateralUsed", 0);
            collateralReturn = hItem.getDouble("collateralReturn", 0);
            repayType = LoanRepayType.reachEnumConstant(hItem.getInt("repayType"));
        }

        /**
         * Method to get {@link #collateralUsed} instance <br>
         * No-any params required
         *
         * @return {@link #collateralUsed} instance as double
         */
        public double getCollateralUsed() {
            return collateralUsed;
        }

        /**
         * Method to get {@link #collateralUsed} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #collateralUsed} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getCollateralUsed(int decimals) {
            return roundValue(collateralUsed, decimals);
        }

        /**
         * Method to get {@link #collateralReturn} instance <br>
         * No-any params required
         *
         * @return {@link #collateralReturn} instance as double
         */
        public double getCollateralReturn() {
            return collateralReturn;
        }

        /**
         * Method to get {@link #collateralReturn} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #collateralReturn} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getCollateralReturn(int decimals) {
            return roundValue(collateralReturn, decimals);
        }

        /**
         * Method to get {@link #repayType} instance <br>
         * No-any params required
         *
         * @return {@link #repayType} instance as {@link LoanRepayType}
         */
        public LoanRepayType getRepayType() {
            return repayType;
        }

    }

}
