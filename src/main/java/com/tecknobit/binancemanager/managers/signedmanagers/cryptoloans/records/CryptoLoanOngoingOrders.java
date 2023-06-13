package com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.records.loan.LoanBaseStructure;
import com.tecknobit.binancemanager.managers.records.loan.LoanOrder;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records.CryptoLoanOngoingOrders.LoanOngoingOrder;

/**
 * The {@code CryptoLoanOngoingOrders} class is useful to create a crypto loan ongoing orders
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#borrow-get-loan-ongoing-orders-user_data">
 * Borrow - Get Loan Ongoing Orders (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class CryptoLoanOngoingOrders extends BinanceRowsList<LoanOngoingOrder> {

    /**
     * Constructor to init {@link CryptoLoanOngoingOrders} object
     *
     * @param total  : number of orders
     * @param orders :  list of the orders
     */
    public CryptoLoanOngoingOrders(int total, ArrayList<LoanOngoingOrder> orders) {
        super(total, orders);
    }

    /**
     * Constructor to init {@link CryptoLoanOngoingOrders}
     *
     * @param jList : list details as {@link JSONObject}
     */
    public CryptoLoanOngoingOrders(JSONObject jList) {
        super(jList);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new LoanOngoingOrder((JSONObject) row));
    }

    /**
     * The {@code LoanOngoingOrder} class is useful to create a crypto loan ongoing order
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see LoanBaseStructure
     * @see LoanOrder
     */
    public static class LoanOngoingOrder extends LoanOrder {

        /**
         * {@code collateralAmount} collateral amount of the loan ongoing order
         */
        private final double collateralAmount;

        /**
         * Constructor to init {@link LoanOngoingOrder} object
         *
         * @param loanCoin          :            coin of the loan ongoing order
         * @param collateralCoin    :      collateral coin of the loan ongoing order
         * @param orderId           :             id of the loan ongoing order
         * @param totalDebt         :           total debit of the loan ongoing order
         * @param residualInterest  :    residual interest of the loan ongoing order
         * @param currentLTV        :          current LTV of the loan ongoing order
         * @param expirationTime    :      expiration time of the loan ongoing order
         * @param collateralAmount: collateral amount of the loan ongoing order
         */
        public LoanOngoingOrder(String loanCoin, String collateralCoin, long orderId, double totalDebt,
                                double residualInterest, double currentLTV, long expirationTime, double collateralAmount) {
            super(loanCoin, collateralCoin, orderId, totalDebt, residualInterest, currentLTV, expirationTime);
            this.collateralAmount = collateralAmount;
        }

        /**
         * Constructor to init {@link LoanOngoingOrder} object
         *
         * @param jLoanOngoingOrder : loan ongoing order details as {@link JSONObject}
         */
        public LoanOngoingOrder(JSONObject jLoanOngoingOrder) {
            super(jLoanOngoingOrder);
            collateralAmount = hItem.getDouble("collateralAmount", 0);
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

    }

}
