package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MarginInterestHistory {

    private final int total;
    private ArrayList<MarginInterestAsset> marginInterestAssets;

    public MarginInterestHistory(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("rows");
        total = jsonArray.length();
        loadMarginInterestAssets(jsonArray);
    }

    private void loadMarginInterestAssets(JSONArray jsonArray) {
        marginInterestAssets = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject jsonObject = jsonArray.getJSONObject(j);
            marginInterestAssets.add(new MarginInterestAsset(jsonObject.getString("isolatedSymbol"),
                    jsonObject.getString("asset"),
                    jsonObject.getDouble("interest"),
                    jsonObject.getLong("interestAccuredTime"),
                    jsonObject.getDouble("interestRate"),
                    jsonObject.getDouble("principal"),
                    jsonObject.getString("type")
            ));
        }
    }

    public int getTotal() {
        return total;
    }

    public ArrayList<MarginInterestAsset> getMarginInterestAssetsList() {
        return marginInterestAssets;
    }

    public MarginInterestAsset getMarginInterestAsset(int index) {
        return marginInterestAssets.get(index);
    }

    public static class MarginInterestAsset {

        private final String isolatedSymbol;
        private final String asset;
        private final double interest;
        private final long interestAccuredTime;
        private final double interestRate;
        private final double principal;
        private final String type;

        public MarginInterestAsset(String isolatedSymbol, String asset, double interest, long interestAccuredTime,
                                   double interestRate, double principal, String type) {
            this.isolatedSymbol = isolatedSymbol;
            this.asset = asset;
            this.interest = interest;
            this.interestAccuredTime = interestAccuredTime;
            this.interestRate = interestRate;
            this.principal = principal;
            this.type = type;
        }

        public String getIsolatedSymbol() {
            return isolatedSymbol;
        }

        public String getAsset() {
            return asset;
        }

        public double getInterest() {
            return interest;
        }

        public long getInterestAccuredTime() {
            return interestAccuredTime;
        }

        public double getInterestRate() {
            return interestRate;
        }

        public double getPrincipal() {
            return principal;
        }

        public String getType() {
            return type;
        }

    }
}
