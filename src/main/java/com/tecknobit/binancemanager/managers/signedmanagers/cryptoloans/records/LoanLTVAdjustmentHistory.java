package com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records;

import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.records.loan.LoanBaseStructure;
import com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records.CryptoLoanAdjustLTV.LoanAdjustDirection;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records.LoanLTVAdjustmentHistory.LoanLTVAdjustment;

public class LoanLTVAdjustmentHistory extends BinanceRowsList<LoanLTVAdjustment> {

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     **/
    public LoanLTVAdjustmentHistory(int total, ArrayList<LoanLTVAdjustment> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jList : list details as {@link JSONObject}
     **/
    public LoanLTVAdjustmentHistory(JSONObject jList) {
        super(jList);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new LoanLTVAdjustment((JSONObject) row));
    }

    public static class LoanLTVAdjustment extends LoanBaseStructure {

        private final LoanAdjustDirection direction;
        private final double amount;
        private final double preLTV;
        private final double afterLTV;
        private final long adjustTime;
        private final long orderId;

        /**
         * Constructor to init {@link LoanBaseStructure} object
         *
         * @param loanCoin       :       coin of the loan
         * @param collateralCoin : collateral coin of the loan
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
         * Constructor to init {@link LoanBaseStructure} object
         *
         * @param jLoanLTVAdjustment : loan base structure details as {@link JSONObject}
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

        public LoanAdjustDirection getDirection() {
            return direction;
        }

        public double getAmount() {
            return amount;
        }

        public double getPreLTV() {
            return preLTV;
        }

        public double getAfterLTV() {
            return afterLTV;
        }

        public long getAdjustTime() {
            return adjustTime;
        }

        public long getOrderId() {
            return orderId;
        }

    }

}
