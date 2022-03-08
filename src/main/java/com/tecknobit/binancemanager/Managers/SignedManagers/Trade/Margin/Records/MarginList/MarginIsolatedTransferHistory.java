package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MarginIsolatedTransferHistory {

    public static final String TRANSFER_SPOT = "SPOT";
    public static final String TRANSFER_ISOLATED_MARGIN = "ISOLATED_MARGIN";
    private final int total;
    private ArrayList<MarginIsolatedTransfer> marginIsolatedTransfers;

    public MarginIsolatedTransferHistory(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("rows");
        total = jsonArray.length();
        loadIsolatedTransfersList(jsonArray);
    }

    private void loadIsolatedTransfersList(JSONArray jsonArray) {
        marginIsolatedTransfers = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject isolatedTransfer = jsonArray.getJSONObject(j);
            marginIsolatedTransfers.add(new MarginIsolatedTransfer(isolatedTransfer.getString("asset"),
                    isolatedTransfer.getLong("txId"),
                    isolatedTransfer.getLong("timestamp"),
                    isolatedTransfer.getString("status"),
                    isolatedTransfer.getDouble("amount"),
                    isolatedTransfer.getString("transFrom"),
                    isolatedTransfer.getString("transTO")
            ));
        }
    }

    public int getTotal() {
        return total;
    }

    public ArrayList<MarginIsolatedTransfer> getMarginIsolatedTransfersList() {
        return marginIsolatedTransfers;
    }

    public MarginIsolatedTransfer getMarginIsolatedTransfer(int index) {
        return marginIsolatedTransfers.get(index);
    }

    public static class MarginIsolatedTransfer extends MarginAssetList {

        private final double amount;
        private final String transFrom;
        private final String transTo;

        public MarginIsolatedTransfer(String asset, long txId, long timestamp, String status, double amount,
                                      String transFrom, String transTo) {
            super(asset, txId, timestamp, status);
            this.amount = amount;
            this.transFrom = transFrom;
            this.transTo = transTo;
        }

        public double getAmount() {
            return amount;
        }

        public String getTransFrom() {
            return transFrom;
        }

        public String getTransTo() {
            return transTo;
        }

    }

}
