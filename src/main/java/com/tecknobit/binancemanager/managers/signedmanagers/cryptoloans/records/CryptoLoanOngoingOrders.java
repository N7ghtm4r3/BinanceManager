package com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records;

import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.records.loan.LoanOrder;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records.CryptoLoanOngoingOrders.LoanOngoingOrder;

public class CryptoLoanOngoingOrders extends BinanceRowsList<LoanOngoingOrder> {

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total  : number of items
     * @param orders :  list of the items
     **/
    public CryptoLoanOngoingOrders(int total, ArrayList<LoanOngoingOrder> orders) {
        super(total, orders);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jList : list details as {@link JSONObject}
     **/
    public CryptoLoanOngoingOrders(JSONObject jList) {
        super(jList);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new LoanOngoingOrder((JSONObject) row));
    }

    public static class LoanOngoingOrder extends LoanOrder {

        private final double collateralAmount;

        /**
         * Constructor to init {@link LoanOrder} object
         *
         * @param loanCoin         :            coin of the loan order
         * @param collateralCoin   :      collateral coin of the loan order
         * @param orderId          :             id of the loan order
         * @param totalDebt        :           total debit of the loan order
         * @param residualInterest :    residual interest of the loan order
         * @param currentLTV       :          current LTV of the loan order
         * @param expirationTime   :      expiration time of the loan order
         **/
        public LoanOngoingOrder(String loanCoin, String collateralCoin, long orderId, double totalDebt,
                                double residualInterest, double currentLTV, long expirationTime, double collateralAmount) {
            super(loanCoin, collateralCoin, orderId, totalDebt, residualInterest, currentLTV, expirationTime);
            this.collateralAmount = collateralAmount;
        }

        /**
         * Constructor to init {@link LoanOrder} object
         *
         * @param jLoanOngoingOrder : loan order details as {@link JSONObject}
         **/
        public LoanOngoingOrder(JSONObject jLoanOngoingOrder) {
            super(jLoanOngoingOrder);
            collateralAmount = hItem.getDouble("collateralAmount", 0);
        }

        public double getCollateralAmount() {
            return collateralAmount;
        }

    }

}
