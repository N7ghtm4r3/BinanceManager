package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MarginRepay {

    private final int total;
    private ArrayList<MarginRepayAsset> marginRepayAssets;

    public MarginRepay(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("rows");
        total = jsonArray.length();
        loadMarginRepayAssets(jsonArray);
    }

    private void loadMarginRepayAssets(JSONArray jsonArray) {
        marginRepayAssets = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject jsonObject = jsonArray.getJSONObject(j);
            marginRepayAssets.add(new MarginRepayAsset(jsonObject.getString("asset"),
                    jsonObject.getLong("txId"),
                    jsonObject.getLong("timestamp"),
                    jsonObject.getString("status"),
                    jsonObject.getString("isolatedSymbol"),
                    jsonObject.getDouble("principal"),
                    jsonObject.getDouble("amount"),
                    jsonObject.getDouble("interest")
            ));
        }
    }

    public int getTotal() {
        return total;
    }

    public ArrayList<MarginRepayAsset> getMarginRepayAssetsList() {
        return marginRepayAssets;
    }

    public MarginRepayAsset getMarginRepayAsset(int index) {
        return marginRepayAssets.get(index);
    }

    public static class MarginRepayAsset extends MarginLoan.MarginLoanAsset {

        private final double amount;
        private final double interest;

        public MarginRepayAsset(String asset, long txId, long timestamp, String status, String isolatedSymbol,
                                double principal, double amount, double interest) {
            super(asset, txId, timestamp, status, isolatedSymbol, principal);
            this.amount = amount;
            this.interest = interest;
        }

        public double getAmount() {
            return amount;
        }

        public double getInterest() {
            return interest;
        }

    }

}
