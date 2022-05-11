package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Asset;

import java.util.ArrayList;

/**
 * The {@code AssetDividend} class is useful to manage AssetDividend Binance request
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data">https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data</a>
 * **/

public class AssetDividend {

    private int total;
    private ArrayList<AssetDividendDetails> assetDividendDetailsList;

    public AssetDividend(int total, ArrayList<AssetDividendDetails> assetDividendDetails) {
        this.total = total;
        this.assetDividendDetailsList = assetDividendDetails;
    }

    public int total() {
        return total;
    }

    public void setTotal(int total) {
        if(total < 0)
            throw new IllegalArgumentException("Total value cannot be less than 0");
        this.total = total;
    }

    public ArrayList<AssetDividendDetails> getAssetDividendDetailsList() {
        return assetDividendDetailsList;
    }

    public void setAssetDividendDetailsList(ArrayList<AssetDividendDetails> assetDividendDetailsList) {
        this.assetDividendDetailsList = assetDividendDetailsList;
    }

    public void insertAssetDividendDetails(AssetDividendDetails assetDividendDetails){
        if(!assetDividendDetailsList.contains(assetDividendDetails)) {
            assetDividendDetailsList.add(assetDividendDetails);
            setTotal(total + 1);
        }
    }

    public boolean removeAssetDividendDetails(AssetDividendDetails assetDividendDetails){
        boolean removed = assetDividendDetailsList.remove(assetDividendDetails);
        if(removed)
            setTotal(total - 1);
        return removed;
    }

    public AssetDividendDetails getAssetDividendDetails(int index){
        return assetDividendDetailsList.get(index);
    }

    /**
     *  The {@code AssetDividendDetails} class is useful to obtain and format AssetDividendDetails object
     *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data">https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data</a>
     * **/

    public static final class AssetDividendDetails {

        private final long id;
        private final double amount;
        private final String asset;
        private final long divTime;
        private final String enInfo;
        private final long tranId;

        public AssetDividendDetails(long id, double amount, String asset, long divTime, String enInfo, long tranId) {
            this.id = id;
            this.amount = amount;
            this.asset = asset;
            this.divTime = divTime;
            this.enInfo = enInfo;
            this.tranId = tranId;
        }

        public long id() {
            return id;
        }

        public double amount() {
            return amount;
        }

        public String asset() {
            return asset;
        }

        public long divTime() {
            return divTime;
        }

        public String enInfo() {
            return enInfo;
        }

        public long tranId() {
            return tranId;
        }

    }

}
