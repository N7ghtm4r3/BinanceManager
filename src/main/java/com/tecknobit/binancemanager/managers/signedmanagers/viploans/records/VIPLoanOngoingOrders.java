package com.tecknobit.binancemanager.managers.signedmanagers.viploans.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.records.loan.LoanBaseStructure;
import com.tecknobit.binancemanager.managers.records.loan.LoanOrder;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.viploans.records.VIPLoanOngoingOrders.VIPLoanOrder;

/**
 * The {@code VIPLoanOngoingOrders} class is useful to create a VIP loan ongoing orders list
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-vip-loan-ongoing-orders-user_data">
 * Get VIP Loan Ongoing Orders (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class VIPLoanOngoingOrders extends BinanceRowsList<VIPLoanOrder> {

    /**
     * Constructor to init {@link VIPLoanOngoingOrders} object
     *
     * @param total  : number of orders
     * @param orders :  list of the orders
     */
    public VIPLoanOngoingOrders(int total, ArrayList<VIPLoanOrder> orders) {
        super(total, orders);
    }

    /**
     * Constructor to init {@link VIPLoanOngoingOrders}
     *
     * @param jList : list details as {@link JSONObject}
     */
    public VIPLoanOngoingOrders(JSONObject jList) {
        super(jList);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new VIPLoanOrder((JSONObject) row));
    }

    /**
     * The {@code VIPLoanOrder} class is useful to create a VIP loan order
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see LoanBaseStructure
     * @see LoanOrder
     */
    public static class VIPLoanOrder extends LoanOrder {

        /**
         * {@code collateralAccountId} collateral account id of the vip loan order
         */
        private final long collateralAccountId;

        /**
         * {@code collateralValue} collateral value of the vip loan order
         */
        private final double collateralValue;

        /**
         * {@code totalCollateralValueAfterHaircut} total collateral value after haircut of the vip loan order
         */
        private final double totalCollateralValueAfterHaircut;

        /**
         * {@code lockedCollateralValue} locked collateral value of the vip loan order
         */
        private final double lockedCollateralValue;

        /**
         * Constructor to init {@link VIPLoanOrder} object
         *
         * @param loanCoin                          :            coin of the vip loan order
         * @param collateralCoin                    :      collateral coin of the vip loan order
         * @param orderId                           :             id of the vip loan order
         * @param totalDebt                         :           total debit of the vip loan order
         * @param residualInterest                  :    residual interest of the vip loan order
         * @param currentLTV                        :          current LTV of the vip loan order
         * @param expirationTime                    :      expiration time of the vip loan order
         * @param collateralAccountId               : collateral account id of the vip loan order
         * @param collateralValue                   :     collateral value of the vip loan order
         * @param totalCollateralValueAfterHaircut: total collateral value after haircut of the vip loan order
         * @param lockedCollateralValue:            locked collateral value of the vip loan order
         */
        public VIPLoanOrder(String loanCoin, String collateralCoin, long orderId, double totalDebt, double residualInterest,
                            double currentLTV, long expirationTime, long collateralAccountId, double collateralValue,
                            double totalCollateralValueAfterHaircut, double lockedCollateralValue) {
            super(loanCoin, collateralCoin, orderId, totalDebt, residualInterest, currentLTV, expirationTime);
            this.collateralAccountId = collateralAccountId;
            this.collateralValue = collateralValue;
            this.totalCollateralValueAfterHaircut = totalCollateralValueAfterHaircut;
            this.lockedCollateralValue = lockedCollateralValue;
        }

        /**
         * Constructor to init {@link VIPLoanOrder} object
         *
         * @param jVIPLoanOrder: VIP loan order details as {@link JSONObject}
         */
        public VIPLoanOrder(JSONObject jVIPLoanOrder) {
            super(jVIPLoanOrder);
            collateralAccountId = hItem.getLong("collateralAccountId", 0);
            collateralValue = hItem.getDouble("collateralValue", 0);
            totalCollateralValueAfterHaircut = hItem.getDouble("totalCollateralValueAfterHaircut", 0);
            lockedCollateralValue = hItem.getDouble("lockedCollateralValue", 0);
        }

        /**
         * Method to get {@link #collateralAccountId} instance <br>
         * No-any params required
         *
         * @return {@link #collateralAccountId} instance as long
         */
        public long getCollateralAccountId() {
            return collateralAccountId;
        }

        /**
         * Method to get {@link #collateralValue} instance <br>
         * No-any params required
         *
         * @return {@link #collateralValue} instance as double
         */
        public double getCollateralValue() {
            return collateralValue;
        }

        /**
         * Method to get {@link #collateralValue} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #collateralValue} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getCollateralValue(int decimals) {
            return roundValue(collateralValue, decimals);
        }

        /**
         * Method to get {@link #totalCollateralValueAfterHaircut} instance <br>
         * No-any params required
         *
         * @return {@link #totalCollateralValueAfterHaircut} instance as double
         */
        public double getTotalCollateralValueAfterHaircut() {
            return totalCollateralValueAfterHaircut;
        }

        /**
         * Method to get {@link #totalCollateralValueAfterHaircut} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #totalCollateralValueAfterHaircut} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getTotalCollateralValueAfterHaircut(int decimals) {
            return roundValue(totalCollateralValueAfterHaircut, decimals);
        }

        /**
         * Method to get {@link #lockedCollateralValue} instance <br>
         * No-any params required
         *
         * @return {@link #lockedCollateralValue} instance as double
         */
        public double getLockedCollateralValue() {
            return lockedCollateralValue;
        }

        /**
         * Method to get {@link #lockedCollateralValue} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #lockedCollateralValue} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getLockedCollateralValue(int decimals) {
            return roundValue(lockedCollateralValue, decimals);
        }

    }

}
