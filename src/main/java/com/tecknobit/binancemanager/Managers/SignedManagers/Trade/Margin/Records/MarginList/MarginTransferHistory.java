package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MarginTransferHistory {

    public static final String TYPE_ROLL_IN = "ROLL_IN";
    public static final String TYPE_ROLL_OUT = "ROLL_OUT";
    private final int total;
    private ArrayList<MarginTransferAsset> marginTransferAssets;

    public MarginTransferHistory(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("rows");
        this.total = jsonArray.length();
        loadMarginAssets(jsonArray);
    }

    private void loadMarginAssets(JSONArray jsonArray) {
        marginTransferAssets = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject jsonObject = jsonArray.getJSONObject(j);
            marginTransferAssets.add(new MarginTransferAsset(jsonObject.getString("asset"),
                    jsonObject.getLong("txId"),
                    jsonObject.getDouble("amount"),
                    jsonObject.getString("status"),
                    jsonObject.getLong("timestamp"),
                    jsonObject.getString("type")
            ));
        }
    }

    public int getTotal() {
        return total;
    }

    public ArrayList<MarginTransferAsset> getMarginTransferAssetsList() {
        return marginTransferAssets;
    }

    public MarginTransferAsset getMarginTransferAsset(int index) {
        return marginTransferAssets.get(index);
    }

    public static class MarginTransferAsset extends MarginAssetList {

        private final double amount;
        private final String type;

        public MarginTransferAsset(String asset, long txId, double amount, String status, long timestamp, String type) {
            super(asset, txId, timestamp, status);
            this.amount = amount;
            this.type = type;
        }

        public double getAmount() {
            return amount;
        }

        public String getType() {
            return type;
        }

    }

}
