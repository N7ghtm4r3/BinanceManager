package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Asset;

import java.util.ArrayList;

/**
 * The {@code AssetDividend} class is useful to manage AssetDividend Binance request
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data">
 *     https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data</a>
 * **/

public class AssetDividend {

    /**
     * {@code total} is instance that memorizes total size of {@link #assetDividendDetailsList}
     * **/
    private int total;

    /**
     * {@code assetDividendDetailsList} is instance that memorizes list of {@link AssetDividendDetails}
     * **/
    private ArrayList<AssetDividendDetails> assetDividendDetailsList;

    /** Constructor to init {@link AssetDividend} object
     * @param total: total size of {@link #assetDividendDetailsList}
     * @param assetDividendDetails: list of {@link AssetDividendDetails}
     * **/
    public AssetDividend(int total, ArrayList<AssetDividendDetails> assetDividendDetails) {
        this.total = total;
        this.assetDividendDetailsList = assetDividendDetails;
    }

    public int total() {
        return total;
    }

    public ArrayList<AssetDividendDetails> getAssetDividendDetailsList() {
        return assetDividendDetailsList;
    }

    public void setAssetDividendDetailsList(ArrayList<AssetDividendDetails> assetDividendDetailsList) {
        this.assetDividendDetailsList = assetDividendDetailsList;
        total = assetDividendDetailsList.size();
    }

    public void insertAssetDividendDetails(AssetDividendDetails assetDividendDetails){
        if(!assetDividendDetailsList.contains(assetDividendDetails)) {
            assetDividendDetailsList.add(assetDividendDetails);
            total += 1;
        }
    }

    public boolean removeAssetDividendDetails(AssetDividendDetails assetDividendDetails){
        boolean removed = assetDividendDetailsList.remove(assetDividendDetails);
        if(removed)
            total -= 1;
        return removed;
    }

    public AssetDividendDetails getAssetDividendDetails(int index){
        return assetDividendDetailsList.get(index);
    }

    /**
     *  The {@code AssetDividendDetails} class is useful to obtain and format AssetDividendDetails object
     *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data">
     *      https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data</a>
     * **/

    public static final class AssetDividendDetails {

        /**
         * {@code id} is instance that memorizes asset dividend details identifier
         * **/
        private final long id;

        /**
         * {@code id} is instance that memorizes amount value
         * **/
        private final double amount;

        /**
         * {@code asset} is instance that memorizes asset value
         * **/
        private final String asset;

        /**
         * {@code divTime} is instance that memorizes division time value
         * **/
        private final long divTime;

        /**
         * {@code enInfo} is instance that memorizes en info value
         * **/
        private final String enInfo;

        /**
         * {@code tranId} is instance that memorizes transaction identifier
         * **/
        private final long tranId;

        /** Constructor to init {@link AssetDividendDetails} object
         * @param id: total size of {@link #assetDividendDetailsList}
         * @param amount: asset dividend details identifier
         * @param asset: asset value
         * @param divTime: division time value
         * @param enInfo: en info value
         * @param tranId: transaction identifier
         * @throws IllegalArgumentException if parameters range is not respected
         * **/
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
