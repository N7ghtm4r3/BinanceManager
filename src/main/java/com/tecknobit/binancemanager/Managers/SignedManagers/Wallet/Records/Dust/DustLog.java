package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Dust;

import java.util.ArrayList;

/**
 *  The {@code DustLog} class is useful to manage DustLog Binance request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">
 *      https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data</a>
 * **/

public class DustLog {

    /**
     * {@code total} is instance that memorizes total size about {@link #userAssetDribbletlList}
     * **/
    private int total;

    /**
     * {@code userAssetDribbletlList} is instance that memorizes list of {@link AssetDribblets}
     * **/
    private ArrayList<AssetDribblets> userAssetDribbletlList;

    /** Constructor to init {@link DustLog} object
     * @param total: total size about {@link #userAssetDribbletlList}
     * @param userAssetDribblets: list of {@link AssetDribblets}
     * **/
    public DustLog(int total, ArrayList<AssetDribblets> userAssetDribblets) {
        this.total = total;
        this.userAssetDribbletlList = userAssetDribblets;
    }

    public int total() {
        return total;
    }

    public ArrayList<AssetDribblets> userAssetDribblets() {
        return userAssetDribbletlList;
    }

    public void setUserAssetDribbletlList(ArrayList<AssetDribblets> userAssetDribbletlList) {
        this.userAssetDribbletlList = userAssetDribbletlList;
        total = userAssetDribblets().size();
    }

    public void insertAssetDribblet(AssetDribblets assetDribblets){
        if(!userAssetDribbletlList.contains(assetDribblets)) {
            userAssetDribbletlList.add(assetDribblets);
            total += 1;
        }
    }

    public boolean removeAssetDribblet(AssetDribblets assetDribblets){
        boolean removed = userAssetDribbletlList.remove(assetDribblets);
        if(removed)
            total -= 1;
        return removed;
    }

    public AssetDribblets getAssetDribblets(int index){
        return userAssetDribbletlList.get(index);
    }

    /**
     *  The {@code AssetDribblets} class is useful to obtain and format AssetDribblets object
     *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">
     *      https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data</a>
     * **/

    public static class AssetDribblets {

        /**
         * {@code operateTime} is instance that memorizes operate time value
         * **/
        private final long operateTime;

        /**
         * {@code totalTransferedAmount} is instance that memorizes total transfered amount value
         * **/
        private final double totalTransferedAmount;

        /**
         * {@code totalServiceChargeAmount} is instance that memorizes total service charge amount value
         * **/
        private final double totalServiceChargeAmount;

        /**
         * {@code transId} is instance that memorizes transaction identifier value
         * **/
        private final long transId;

        /**
         * {@code assetDribbletsDetailsList} is instance that memorizes list of {@link DustItem}
         * **/
        private final ArrayList<DustItem> assetDribbletsDetailsList;

        /** Constructor to init {@link AssetDribblets} object
         * @param operateTime: operate time value
         * @param totalTransferedAmount: total transfered amount value
         * @param totalServiceChargeAmount: total service charge amount value
         * @param transId: transaction identifier value
         * @param assetDribbletsDetails: list of {@link DustItem}
         * **/
        public AssetDribblets(long operateTime, double totalTransferedAmount, double totalServiceChargeAmount,
                              long transId, ArrayList<DustItem> assetDribbletsDetails) {
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

        public ArrayList<DustItem> assetDribbletsDetails() {
            return assetDribbletsDetailsList;
        }

        public ArrayList<DustItem> getAssetDribbletsDetailsList() {
            return assetDribbletsDetailsList;
        }

        public void insertAssetDribbletDetails(DustItem assetDribbletsDetails){
            if(!assetDribbletsDetailsList.contains(assetDribbletsDetails))
                assetDribbletsDetailsList.add(assetDribbletsDetails);
        }

        public boolean removeAssetDribbletDetails(DustItem assetDribbletsDetails){
            return assetDribbletsDetailsList.remove(assetDribbletsDetails);
        }

        public DustItem getAssetDribbletDetails(int index){
            return assetDribbletsDetailsList.get(index);
        }

    }

}
