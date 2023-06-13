package com.tecknobit.binancemanager.managers.signedmanagers.fiat.records;

import com.tecknobit.binancemanager.managers.BinanceManager.BinanceResponse;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceDataList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.fiat.records.FiatPaymentsHistory.FiatPayment;

/**
 * The {@code FiatPaymentsHistory} class is useful to format a fiat payments list
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-fiat-payments-history-user_data">
 * Get Fiat Payments History (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceDataList
 * @see BinanceResponse
 */
public class FiatPaymentsHistory extends BinanceDataList<FiatPayment> {

    /**
     * Constructor to init {@link FiatPaymentsHistory} object
     *
     * @param total:   number of items
     * @param success: whether the list is created with success
     * @param data:    {@link FiatPayment} of the list
     */
    public FiatPaymentsHistory(int total, boolean success, ArrayList<FiatPayment> data) {
        super(total, success, data);
    }

    /**
     * Constructor to init {@link FiatPaymentsHistory} object
     *
     * @param jFiatPayments: fiat payments details as {@link JSONObject}
     */
    public FiatPaymentsHistory(JSONObject jFiatPayments) {
        super(jFiatPayments);
        JSONArray jData = hItem.getJSONArray("data", new JSONArray());
        for (int j = 0; j < jData.length(); j++)
            data.add(new FiatPayment(jData.getJSONObject(j)));
    }

    /**
     * The {@code FiatPayment} class is useful to format a fiat payment
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see FiatItem
     */
    public static class FiatPayment extends FiatItem {

        /**
         * {@code PaymentMethod} list of available payment methods
         */
        public enum PaymentMethod {

            /**
             * {@code Cash Balance} payment method
             */
            Cash_Balance("Cash Balance"),

            /**
             * {@code Credit Card} payment method
             */
            Credit_Card("Credit Card"),

            /**
             * {@code Online Banking} payment method
             */
            Online_Banking("Online Banking"),

            /**
             * {@code Bank Transfer} payment method
             */
            Bank_Transfer("Bank Transfer");

            /**
             * {@code method} of the payment
             */
            private final String method;

            /**
             * Constructor to init {@link PaymentMethod}
             *
             * @param method: of the payment
             */
            PaymentMethod(String method) {
                this.method = method;
            }

            /**
             * Method to get {@link #method} instance <br>
             * No-any params required
             *
             * @return {@link #method} instance as {@link String}
             */
            @Override
            public String toString() {
                return method;
            }

        }

        /**
         * {@code sourceAmount} fiat trade amount
         */
        private final double sourceAmount;

        /**
         * {@code obtainAmount} crypto trade amount
         */
        private final double obtainAmount;

        /**
         * {@code cryptocurrency} crypto token
         */
        private final String cryptocurrency;

        /**
         * {@code price} of the payment
         */
        private final double price;

        /**
         * {@code paymentMethod} method of the payment
         */
        private final PaymentMethod paymentMethod;

        /**
         * Constructor to init {@link FiatPayment} object
         *
         * @param orderNo:        order number
         * @param fiatCurrency:   fiat token
         * @param totalFee:       trade fee
         * @param status:         fiat item status
         * @param createTime:     creation time of the fiat item
         * @param updateTime:     update time of the fiat item
         * @param sourceAmount:   fiat trade amount
         * @param obtainAmount:   crypto trade amount
         * @param cryptocurrency: crypto token
         * @param price:          price of the payment
         * @param paymentMethod:  method of the payment
         */
        public FiatPayment(String orderNo, String fiatCurrency, double totalFee, FiatStatus status, long createTime,
                           long updateTime, double sourceAmount, double obtainAmount, String cryptocurrency, double price,
                           PaymentMethod paymentMethod) {
            super(orderNo, fiatCurrency, totalFee, status, createTime, updateTime);
            this.sourceAmount = sourceAmount;
            this.obtainAmount = obtainAmount;
            this.cryptocurrency = cryptocurrency;
            this.price = price;
            this.paymentMethod = paymentMethod;
        }

        /**
         * Constructor to init {@link FiatPayment} object
         *
         * @param jFiatPayment: fiat payment details as {@link JSONObject}
         */
        public FiatPayment(JSONObject jFiatPayment) {
            super(jFiatPayment);
            sourceAmount = hItem.getDouble("sourceAmount", 0);
            obtainAmount = hItem.getDouble("obtainAmount", 0);
            cryptocurrency = hItem.getString("cryptoCurrency");
            price = hItem.getDouble("price", 0);
            paymentMethod = PaymentMethod.valueOf(hItem.getString("paymentMethod").replace(" ", "_"));
        }

        /**
         * Method to get {@link #sourceAmount} instance <br>
         * No-any params required
         *
         * @return {@link #sourceAmount} instance as double
         */
        public double getSourceAmount() {
            return sourceAmount;
        }

        /**
         * Method to get {@link #sourceAmount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #sourceAmount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getSourceAmount(int decimals) {
            return roundValue(sourceAmount, decimals);
        }

        /**
         * Method to get {@link #obtainAmount} instance <br>
         * No-any params required
         *
         * @return {@link #obtainAmount} instance as double
         */
        public double getObtainAmount() {
            return obtainAmount;
        }

        /**
         * Method to get {@link #obtainAmount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #obtainAmount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getObtainAmount(int decimals) {
            return roundValue(obtainAmount, decimals);
        }

        /**
         * Method to get {@link #cryptocurrency} instance <br>
         * No-any params required
         *
         * @return {@link #cryptocurrency} instance as {@link String}
         */
        public String getCryptocurrency() {
            return cryptocurrency;
        }

        /**
         * Method to get {@link #price} instance <br>
         * No-any params required
         *
         * @return {@link #price} instance as double
         */
        public double getPrice() {
            return price;
        }

        /**
         * Method to get {@link #price} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #price} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getPrice(int decimals) {
            return roundValue(price, decimals);
        }

        /**
         * Method to get {@link #paymentMethod} instance <br>
         * No-any params required
         *
         * @return {@link #paymentMethod} instance as {@link PaymentMethod}
         */
        public PaymentMethod getPaymentMethod() {
            return paymentMethod;
        }

    }

}
