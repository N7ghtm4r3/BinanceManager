package com.tecknobit.binancemanager.managers.signedmanagers.fiat.records;

import com.tecknobit.binancemanager.managers.records.lists.BinanceDataList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.fiat.records.FiatOperations.FiatOperation;

public class FiatOperations extends BinanceDataList<FiatOperation> {

    public FiatOperations(int total, boolean success, ArrayList<FiatOperation> data) {
        super(total, success, data);
    }

    public FiatOperations(JSONObject jFiatItem) {
        super(jFiatItem);
        JSONArray jData = hItem.getJSONArray("data", new JSONArray());
        for (int j = 0; j < jData.length(); j++)
            data.add(new FiatOperation(jData.getJSONObject(j)));
    }

    public static class FiatOperation extends FiatItem {

        private final double indicatedAmount;
        private final double amount;
        private final String method;

        public FiatOperation(String orderNo, String fiatCurrency, double totalFee, FiatItem.FiatStatus status, long createTime,
                             long updateTime, double indicatedAmount, double amount, String method) {
            super(orderNo, fiatCurrency, totalFee, status, createTime, updateTime);
            this.indicatedAmount = indicatedAmount;
            this.amount = amount;
            this.method = method;
        }

        public FiatOperation(JSONObject jFiatOperation) {
            super(jFiatOperation);
            indicatedAmount = hItem.getDouble("indicatedAmount", 0);
            amount = hItem.getDouble("amount", 0);
            method = hItem.getString("method");
        }

        public double getIndicatedAmount() {
            return indicatedAmount;
        }

        public double getAmount() {
            return amount;
        }

        public String getMethod() {
            return method;
        }

    }

}
