package com.tecknobit.binancemanager.managers.signedmanagers.fiat.records;

import com.tecknobit.binancemanager.managers.records.lists.BinanceDataList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class FiatPayments extends BinanceDataList<FiatPayments.FiatPayment> {

    public FiatPayments(int total, boolean success, ArrayList<FiatPayment> data) {
        super(total, success, data);
    }

    public FiatPayments(JSONObject jFiatItem) {
        super(jFiatItem);
        JSONArray jData = hItem.getJSONArray("data", new JSONArray());
        for (int j = 0; j < jData.length(); j++)
            data.add(new FiatPayment(jData.getJSONObject(j)));
    }

    public static class FiatPayment extends FiatItem {

        public enum PaymentMethod {

            Cash_Balance("Cash Balance"),
            Credit_Card("Credit Card"),
            Online_Banking("Online Banking"),
            Bank_Transfer("Bank Transfer");

            private final String method;

            PaymentMethod(String method) {
                this.method = method;
            }

            @Override
            public String toString() {
                return method;
            }

        }

        private final double sourceAmount;
        private final double obtainAmount;
        private final String cryptocurrency;
        private final double price;
        private final PaymentMethod paymentMethod;

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

        public FiatPayment(JSONObject jFiatItem) {
            super(jFiatItem);
            sourceAmount = hItem.getDouble("sourceAmount", 0);
            obtainAmount = hItem.getDouble("obtainAmount", 0);
            cryptocurrency = hItem.getString("cryptoCurrency");
            price = hItem.getDouble("price", 0);
            paymentMethod = PaymentMethod.valueOf(hItem.getString("paymentMethod").replace(" ", "_"));
        }

        public double getSourceAmount() {
            return sourceAmount;
        }

        public double getObtainAmount() {
            return obtainAmount;
        }

        public String getCryptocurrency() {
            return cryptocurrency;
        }

        public double getPrice() {
            return price;
        }

        public PaymentMethod getPaymentMethod() {
            return paymentMethod;
        }

    }

}
