package com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records.CryptoLoanCustomizeMarginCall.MarginCall;

/**
 * The {@code CryptoLoanCustomizeMarginCall} class is useful to create a crypto loan customize margin call
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#crypto-loan-customize-margin-call-trade">
 * Crypto Loan Customize Margin Call (TRADE)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class CryptoLoanCustomizeMarginCall extends BinanceRowsList<MarginCall> {

    /**
     * Constructor to init {@link CryptoLoanCustomizeMarginCall} object
     *
     * @param total : number of calls
     * @param calls :  list of the calls
     */
    public CryptoLoanCustomizeMarginCall(int total, ArrayList<MarginCall> calls) {
        super(total, calls);
    }

    /**
     * Constructor to init {@link CryptoLoanCustomizeMarginCall}
     *
     * @param jList : list details as {@link JSONObject}
     */
    public CryptoLoanCustomizeMarginCall(JSONObject jList) {
        super(jList);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new MarginCall((JSONObject) row));
    }

    /**
     * The {@code MarginCall} class is useful to create margin call
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class MarginCall extends BinanceItem {

        /**
         * {@code orderId} order id of the margin call
         */
        private final long orderId;

        /**
         * {@code collateralCoin} collateral coin of the margin call
         */
        private final String collateralCoin;

        /**
         * {@code preMarginCall} pre margin call of the margin call
         */
        private final double preMarginCall;

        /**
         * {@code afterMarginCall} after margin call of the margin call
         */
        private final double afterMarginCall;

        /**
         * {@code customizeTime} customize time of the margin call
         */
        private final long customizeTime;

        /**
         * Constructor to init {@link MarginCall}
         *
         * @param orderId         : order id of the margin call
         * @param collateralCoin  : collateral coin of the margin call
         * @param preMarginCall   : pre margin call of the margin call
         * @param afterMarginCall : after margin call of the margin call
         * @param customizeTime   : customize time of the margin call
         */
        public MarginCall(long orderId, String collateralCoin, double preMarginCall, double afterMarginCall,
                          long customizeTime) {
            super(null);
            this.orderId = orderId;
            this.collateralCoin = collateralCoin;
            this.preMarginCall = preMarginCall;
            this.afterMarginCall = afterMarginCall;
            this.customizeTime = customizeTime;
        }

        /**
         * Constructor to init {@link MarginCall}
         *
         * @param jMarginCall : margin call details as {@link JSONObject}
         */
        public MarginCall(JSONObject jMarginCall) {
            super(jMarginCall);
            orderId = hItem.getLong("orderId", 0);
            collateralCoin = hItem.getString("collateralCoin");
            preMarginCall = hItem.getDouble("preMarginCall", 0);
            afterMarginCall = hItem.getDouble("afterMarginCall", 0);
            customizeTime = hItem.getLong("customizeTime", 0);
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
         * Method to get {@link #collateralCoin} instance <br>
         * No-any params required
         *
         * @return {@link #collateralCoin} instance as {@link String}
         */
        public String getCollateralCoin() {
            return collateralCoin;
        }

        /**
         * Method to get {@link #preMarginCall} instance <br>
         * No-any params required
         *
         * @return {@link #preMarginCall} instance as double
         */
        public double getPreMarginCall() {
            return preMarginCall;
        }

        /**
         * Method to get {@link #preMarginCall} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #preMarginCall} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getPreMarginCall(int decimals) {
            return roundValue(preMarginCall, decimals);
        }

        /**
         * Method to get {@link #afterMarginCall} instance <br>
         * No-any params required
         *
         * @return {@link #afterMarginCall} instance as double
         */
        public double getAfterMarginCall() {
            return afterMarginCall;
        }

        /**
         * Method to get {@link #afterMarginCall} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #afterMarginCall} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getAfterMarginCall(int decimals) {
            return roundValue(afterMarginCall, decimals);
        }

        /**
         * Method to get {@link #customizeTime} instance <br>
         * No-any params required
         *
         * @return {@link #customizeTime} instance as long
         */
        public long getCustomizeTime() {
            return customizeTime;
        }

        /**
         * Method to get {@link #customizeTime} instance <br>
         * No-any params required
         *
         * @return {@link #customizeTime} instance as {@link Date}
         */
        public Date getCustomizeDate() {
            return TimeFormatter.getDate(customizeTime);
        }

    }

}
