package com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records;

import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.records.loan.LoanBaseStructure;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records.CryptoLoanBorrowHistory.LoanBorrowHistoryItem;

public class CryptoLoanBorrowHistory extends BinanceRowsList<LoanBorrowHistoryItem> {

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     **/
    public CryptoLoanBorrowHistory(int total, ArrayList<LoanBorrowHistoryItem> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jHistory : list details as {@link JSONObject}
     **/
    public CryptoLoanBorrowHistory(JSONObject jHistory) {
        super(jHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new LoanBorrowHistoryItem((JSONObject) row));
    }

    public static class LoanBorrowHistoryItem extends LoanBaseStructure {

        public enum BorrowStatus {

            Accruing_Interest,
            Overdue,
            Liquidating,
            Repaying,
            Repaid,
            Liquidated,
            Pending,
            Failed

        }

        private final long orderId;
        private final double initialLoanAmount;
        private final double hourlyInterestRate;
        private final int loanTerm;
        private final double initialCollateralAmount;
        private final long borrowTime;
        private final BorrowStatus status;

        /**
         * Constructor to init {@link LoanBaseStructure} object
         *
         * @param loanCoin       :       coin of the vip loan
         * @param collateralCoin : collateral coin of the vip loan
         **/
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
         * Constructor to init {@link LoanBaseStructure} object
         *
         * @param jLoanBorrowHistoryItem : VIP loan base structure details as {@link JSONObject}
         **/
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

        public long getOrderId() {
            return orderId;
        }

        public double getInitialLoanAmount() {
            return initialLoanAmount;
        }

        public double getHourlyInterestRate() {
            return hourlyInterestRate;
        }

        public int getLoanTerm() {
            return loanTerm;
        }

        public double getInitialCollateralAmount() {
            return initialCollateralAmount;
        }

        public long getBorrowTime() {
            return borrowTime;
        }

        public BorrowStatus getStatus() {
            return status;
        }

    }

}
