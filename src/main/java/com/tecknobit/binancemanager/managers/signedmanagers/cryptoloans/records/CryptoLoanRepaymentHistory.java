package com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records;

import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records.CryptoLoanRepay.LoanRepayType;
import com.tecknobit.binancemanager.managers.signedmanagers.viploans.records.VIPLoanRepaymentHistory.VIPLoanRepayment;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records.CryptoLoanRepaymentHistory.CryptoLoanRepayment;

public class CryptoLoanRepaymentHistory extends BinanceRowsList<CryptoLoanRepayment> {

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total      : number of items
     * @param repayments :  list of the items
     **/
    public CryptoLoanRepaymentHistory(int total, ArrayList<CryptoLoanRepayment> repayments) {
        super(total, repayments);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jList : list details as {@link JSONObject}
     **/
    public CryptoLoanRepaymentHistory(JSONObject jList) {
        super(jList);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new CryptoLoanRepayment((JSONObject) row));
    }

    public static class CryptoLoanRepayment extends VIPLoanRepayment {

        private final double collateralUsed;
        private final double collateralReturn;
        private final LoanRepayType repayType;

        /**
         * Constructor to init {@link CryptoLoanRepayment} object
         *
         * @param loanCoin       :       coin of the vip loan
         * @param collateralCoin : collateral coin of the vip loan
         * @param repayStatus    :    repay status of the loan
         * @param repayTime      :      repay time of the VIP loan repayment
         * @param orderId        :        order id of the VIP loan repayment
         * @param repayAmount    :    repay amount of the loan
         **/
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
         * @param jCryptoLoanRepayment : loan repayment  details as {@link JSONObject}
         **/
        public CryptoLoanRepayment(JSONObject jCryptoLoanRepayment) {
            super(jCryptoLoanRepayment);
            collateralUsed = hItem.getDouble("collateralUsed", 0);
            collateralReturn = hItem.getDouble("collateralReturn", 0);
            repayType = LoanRepayType.reachEnumConstant(hItem.getInt("repayType"));
        }

        public double getCollateralUsed() {
            return collateralUsed;
        }

        public double getCollateralReturn() {
            return collateralReturn;
        }

        public LoanRepayType getRepayType() {
            return repayType;
        }

    }

}
