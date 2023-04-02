package com.tecknobit.binancemanager.managers.signedmanagers.viploans.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

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
 **/
public class VIPLoanOngoingOrders extends BinanceRowsList<VIPLoanOrder> {

    /**
     * Constructor to init {@link VIPLoanOngoingOrders} object
     *
     * @param total  : number of orders
     * @param orders :  list of the orders
     **/
    public VIPLoanOngoingOrders(int total, ArrayList<VIPLoanOrder> orders) {
        super(total, orders);
    }

    /**
     * Constructor to init {@link VIPLoanOngoingOrders}
     *
     * @param jList : list details as {@link JSONObject}
     **/
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
     * @see VIPLoanBaseStructure
     **/
    public static class VIPLoanOrder extends VIPLoanBaseStructure {

        /**
         * {@code orderId} id of the vip loan order
         **/
        private final long orderId;

        /**
         * {@code totalDebt} total debit of the vip loan order
         **/
        private final double totalDebt;

        /**
         * {@code residualInterest} residual interest of the vip loan order
         **/
        private final double residualInterest;

        /**
         * {@code collateralAccountId} collateral account id of the vip loan order
         **/
        private final long collateralAccountId;

        /**
         * {@code collateralValue} collateral value of the vip loan order
         **/
        private final double collateralValue;

        /**
         * {@code currentLTV} current LTV of the vip loan order
         **/
        private final double currentLTV;

        /**
         * {@code expirationTime} expiration time of the vip loan order
         **/
        private final long expirationTime;

        /**
         * Constructor to init {@link VIPLoanOrder} object
         *
         * @param loanCoin:            coin of the vip loan order
         * @param collateralCoin:      collateral coin of the vip loan order
         * @param orderId:             id of the vip loan order
         * @param totalDebt:           total debit of the vip loan order
         * @param residualInterest:    residual interest of the vip loan order
         * @param collateralAccountId: collateral account id of the vip loan order
         * @param collateralValue:     collateral value of the vip loan order
         * @param currentLTV:          current LTV of the vip loan order
         * @param expirationTime:      expiration time of the vip loan order
         **/
        public VIPLoanOrder(String loanCoin, String collateralCoin, long orderId, double totalDebt, double residualInterest,
                            long collateralAccountId, double collateralValue, double currentLTV, long expirationTime) {
            super(loanCoin, collateralCoin);
            this.orderId = orderId;
            this.totalDebt = totalDebt;
            this.residualInterest = residualInterest;
            this.collateralAccountId = collateralAccountId;
            this.collateralValue = collateralValue;
            this.currentLTV = currentLTV;
            this.expirationTime = expirationTime;
        }

        /**
         * Constructor to init {@link VIPLoanOrder} object
         *
         * @param jVIPLoanOrder: VIP loan order details as {@link JSONObject}
         **/
        public VIPLoanOrder(JSONObject jVIPLoanOrder) {
            super(jVIPLoanOrder);
            orderId = hItem.getLong("orderId", 0);
            totalDebt = hItem.getDouble("totalDebt", 0);
            residualInterest = hItem.getDouble("residualInterest", 0);
            collateralAccountId = hItem.getLong("collateralAccountId", 0);
            collateralValue = hItem.getDouble("collateralValue", 0);
            currentLTV = hItem.getDouble("currentLTV", 0);
            expirationTime = hItem.getLong("expirationTime", 0);
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

        /**
         * Method to get {@link #totalDebt} instance <br>
         * No-any params required
         *
         * @return {@link #totalDebt} instance as double
         **/
        public double getTotalDebt() {
            return totalDebt;
        }

        /**
         * Method to get {@link #totalDebt} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #totalDebt} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getTotalDebt(int decimals) {
            return roundValue(totalDebt, decimals);
        }

        /**
         * Method to get {@link #residualInterest} instance <br>
         * No-any params required
         *
         * @return {@link #residualInterest} instance as double
         **/
        public double getResidualInterest() {
            return residualInterest;
        }

        /**
         * Method to get {@link #residualInterest} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #residualInterest} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getResidualInterest(int decimals) {
            return roundValue(residualInterest, decimals);
        }

        /**
         * Method to get {@link #collateralAccountId} instance <br>
         * No-any params required
         *
         * @return {@link #collateralAccountId} instance as long
         **/
        public long getCollateralAccountId() {
            return collateralAccountId;
        }

        /**
         * Method to get {@link #collateralValue} instance <br>
         * No-any params required
         *
         * @return {@link #collateralValue} instance as double
         **/
        public double getCollateralValue() {
            return collateralValue;
        }

        /**
         * Method to get {@link #collateralValue} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #collateralValue} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getCollateralValue(int decimals) {
            return roundValue(collateralValue, decimals);
        }

        /**
         * Method to get {@link #currentLTV} instance <br>
         * No-any params required
         *
         * @return {@link #currentLTV} instance as double
         **/
        public double getCurrentLTV() {
            return currentLTV;
        }

        /**
         * Method to get {@link #currentLTV} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #currentLTV} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getCurrentLTV(int decimals) {
            return roundValue(currentLTV, decimals);
        }

        /**
         * Method to get {@link #expirationTime} instance <br>
         * No-any params required
         *
         * @return {@link #expirationTime} instance as long
         **/
        public long getExpirationTime() {
            return expirationTime;
        }

        /**
         * Method to get {@link #expirationTime} instance <br>
         * No-any params required
         *
         * @return {@link #expirationTime} instance as {@link Date}
         **/
        public Date getExpirationDate() {
            return TimeFormatter.getDate(expirationTime);
        }

    }

}
