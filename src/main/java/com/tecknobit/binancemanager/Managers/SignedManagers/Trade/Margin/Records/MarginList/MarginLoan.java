package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MarginLoan {

    private final int total;
    private ArrayList<MarginLoanAsset> marginLoanAssets;

    public MarginLoan(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("rows");
        total = jsonArray.length();
        loadMarginLoanAssets(jsonArray);
    }

    private void loadMarginLoanAssets(JSONArray jsonArray) {
        marginLoanAssets = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject jsonObject = jsonArray.getJSONObject(j);
            marginLoanAssets.add(new MarginLoanAsset(jsonObject.getString("asset"),
                    jsonObject.getLong("txId"),
                    jsonObject.getLong("timestamp"),
                    jsonObject.getString("status"),
                    jsonObject.getString("isolatedSymbol"),
                    jsonObject.getDouble("principal")
            ));
        }
    }

    public int getTotal() {
        return total;
    }

    public ArrayList<MarginLoanAsset> getMarginLoanAssetsList() {
        return marginLoanAssets;
    }

    public MarginLoanAsset getMarginLoanAsset(int index) {
        return marginLoanAssets.get(index);
    }

    public static class MarginLoanAsset extends MarginAssetList {

        private final String isolatedSymbol;
        private final double principal;

        public MarginLoanAsset(String asset, long txId, long timestamp, String status, String isolatedSymbol,
                               double principal) {
            super(asset, txId, timestamp, status);
            this.isolatedSymbol = isolatedSymbol;
            this.principal = principal;
        }

        public String getIsolatedSymbol() {
            return isolatedSymbol;
        }

        public double getPrincipal() {
            return principal;
        }

    }

}
