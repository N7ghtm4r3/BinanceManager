package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Dust;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *  The {@code DustLog} class is useful to manage DustLog Binance request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data</a>
 * **/

public class DustLog {

    private int total;
    private ArrayList<AssetDribblets> userAssetDribbletlList;

    public DustLog(int total, ArrayList<AssetDribblets> userAssetDribblets) {
        this.total = total;
        this.userAssetDribbletlList = userAssetDribblets;
    }

    public int total() {
        return total;
    }

    public void setTotal(int total) {
        if(total < 0)
            throw new IllegalArgumentException("Total value cannot be less than 0");
        this.total = total;
    }

    public ArrayList<AssetDribblets> userAssetDribblets() {
        return userAssetDribbletlList;
    }

    public void setUserAssetDribbletlList(ArrayList<AssetDribblets> userAssetDribbletlList) {
        this.userAssetDribbletlList = userAssetDribbletlList;
    }

    public void insertAssetDribblet(AssetDribblets assetDribblets){
        if(!userAssetDribbletlList.contains(assetDribblets))
            userAssetDribbletlList.add(assetDribblets);
    }

    public boolean removeAssetDribblet(AssetDribblets assetDribblets){
        return userAssetDribbletlList.remove(assetDribblets);
    }

    public AssetDribblets getAssetDribblets(int index){
        try{
            return userAssetDribbletlList.get(index);
        }catch (IndexOutOfBoundsException e){
            throw new IndexOutOfBoundsException(" "+ index);
        }
    }

    /**
     *  The {@code AssetDribblets} class is useful to obtain and format AssetDribblets object
     *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data</a>
     * **/

    public static class AssetDribblets {

        private final long operateTime;
        private final double totalTransferedAmount;
        private final double totalServiceChargeAmount;
        private final long transId;
        private final ArrayList<AssetDribbletsDetails> assetDribbletsDetailsList;

        public AssetDribblets(long operateTime, double totalTransferedAmount, double totalServiceChargeAmount,
                              long transId, ArrayList<AssetDribbletsDetails> assetDribbletsDetails) {
            this.operateTime = operateTime;
            this.totalTransferedAmount = totalTransferedAmount;
            this.totalServiceChargeAmount = totalServiceChargeAmount;
            this.transId = transId;
            this.assetDribbletsDetailsList = assetDribbletsDetails;
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
            return assetDribbletsDetailsList;
        }

        public ArrayList<AssetDribbletsDetails> getAssetDribbletsDetailsList() {
            return assetDribbletsDetailsList;
        }

        public void insertAssetDribbletDetails(AssetDribbletsDetails assetDribbletsDetails){
            if(!assetDribbletsDetailsList.contains(assetDribbletsDetails))
                assetDribbletsDetailsList.add(assetDribbletsDetails);
        }

        public boolean removeAssetDribbletDetails(AssetDribbletsDetails assetDribbletsDetails){
            return assetDribbletsDetailsList.remove(assetDribbletsDetails);
        }

        public AssetDribbletsDetails getAssetDribbletDetails(int index){
            try {
                return assetDribbletsDetailsList.get(index);
            }catch (IndexOutOfBoundsException e){
                throw new IndexOutOfBoundsException(" "+ index);
            }
        }

    }

    /**
     *  The {@code AssetDribbletsDetails} class is useful to obtain and format AssetDribbletsDetails object
     *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data</a>
     * **/

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

        /** Method to assemble an AssetDribbletsDetails list
         * @param #userAssetDribbletDetails: jsonArray obtain by DustLog Binance request
         * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data</a>
         * @return assetDribbletsDetailsList list as ArrayList<AssetDribbletsDetails>
         * **/
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
