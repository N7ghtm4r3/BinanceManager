package com.tecknobit.binancemanager.Managers.Wallet.Records.Dust;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DustLog {

    private final int total;
    private final ArrayList<AssetDribblets> userAssetDribblets;

    public DustLog(int total, ArrayList<AssetDribblets> userAssetDribblets) {
        this.total = total;
        this.userAssetDribblets = userAssetDribblets;
    }

    public int total() {
        return total;
    }

    public ArrayList<AssetDribblets> userAssetDribblets() {
        return userAssetDribblets;
    }

    public static class AssetDribblets {

        private final long operateTime;
        private final double totalTransferedAmount;
        private final double totalServiceChargeAmount;
        private final long transId;
        private final ArrayList<AssetDribbletsDetails> assetDribbletsDetails;

        public AssetDribblets(long operateTime, double totalTransferedAmount, double totalServiceChargeAmount,
                              long transId, ArrayList<AssetDribbletsDetails> assetDribbletsDetails) {
            this.operateTime = operateTime;
            this.totalTransferedAmount = totalTransferedAmount;
            this.totalServiceChargeAmount = totalServiceChargeAmount;
            this.transId = transId;
            this.assetDribbletsDetails = assetDribbletsDetails;
        }

        public long operateTime() {
            return operateTime;
        }

        public double totalTransferedAmount() {
            return totalTransferedAmount;
        }

        public double totalServiceChargeAmount() {
            return totalServiceChargeAmount;
        }

        public long transId() {
            return transId;
        }

        public ArrayList<AssetDribbletsDetails> assetDribbletsDetails() {
            return assetDribbletsDetails;
        }

    }

    public static class AssetDribbletsDetails {

        private final long transId;
        private final double serviceChargeAmount;
        private final double amount;
        private final long operateTime;
        private final double transferedAmount;
        private final String fromAsset;

        public AssetDribbletsDetails(long transId, double serviceChargeAmount, double amount, long operateTime,
                                     double transferedAmount, String fromAsset) {
            this.transId = transId;
            this.serviceChargeAmount = serviceChargeAmount;
            this.amount = amount;
            this.operateTime = operateTime;
            this.transferedAmount = transferedAmount;
            this.fromAsset = fromAsset;
        }

        public long transId() {
            return transId;
        }

        public double serviceChargeAmount() {
            return serviceChargeAmount;
        }

        public double amount() {
            return amount;
        }

        public long operateTime() {
            return operateTime;
        }

        public double transferedAmount() {
            return transferedAmount;
        }

        public String fromAsset() {
            return fromAsset;
        }

        public static ArrayList<AssetDribbletsDetails> getListDribbletsDetails(JSONArray userAssetDribbletDetails) {
            ArrayList<AssetDribbletsDetails> assetDribbletsDetails = new ArrayList<>();
            for (int j = 0; j < userAssetDribbletDetails.length(); j++) {
                JSONObject jsonObject = userAssetDribbletDetails.getJSONObject(j);
                assetDribbletsDetails.add(new AssetDribbletsDetails(jsonObject.getLong("transId"),
                        jsonObject.getDouble("serviceChargeAmount"),
                        jsonObject.getDouble("amount"),
                        jsonObject.getLong("operateTime"),
                        jsonObject.getDouble("transferedAmount"),
                        jsonObject.getString("fromAsset")
                ));
            }
            return assetDribbletsDetails;
        }

    }

}
