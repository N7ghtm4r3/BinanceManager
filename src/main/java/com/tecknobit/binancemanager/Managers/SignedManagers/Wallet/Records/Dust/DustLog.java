package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Dust;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.Tools.Trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Dust.DustItem.getListDribbletsDetails;

/**
 * The {@code DustLog} class is useful to create a Binance's dust log
 *
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data</a>
 **/

public class DustLog {

    /**
     * {@code total} is instance that memorizes total size about {@link #userAssetDribbletlList}
     * **/
    private int total;

    /**
     * {@code userAssetDribbletlList} is instance that memorizes list of {@link AssetDribblets}
     * **/
    private ArrayList<AssetDribblets> userAssetDribbletlList;

    /**
     * Constructor to init {@link DustLog} object
     *
     * @param total:              total size about {@link #userAssetDribbletlList}
     * @param userAssetDribblets: list of {@link AssetDribblets}
     **/
    public DustLog(int total, ArrayList<AssetDribblets> userAssetDribblets) {
        this.total = total;
        this.userAssetDribbletlList = userAssetDribblets;
    }

    /**
     * Constructor to init {@link DustLog} object
     *
     * @param dustLog: dust log details as {@link JSONObject}
     **/
    public DustLog(JSONObject dustLog) {
        total = dustLog.getInt("total");
        userAssetDribbletlList = new ArrayList<>();
        JSONArray assetDribblets = dustLog.getJSONArray("assetDribblets");
        for (int j = 0; j < assetDribblets.length(); j++)
            userAssetDribbletlList.add(new AssetDribblets(assetDribblets.getJSONObject(j)));
    }

    public int getTotal() {
        return total;
    }

    public ArrayList<AssetDribblets> getUserAssetDribbletlList() {
        return userAssetDribbletlList;
    }

    public void setUserAssetDribbletlList(ArrayList<AssetDribblets> userAssetDribbletlList) {
        this.userAssetDribbletlList = userAssetDribbletlList;
        total = userAssetDribbletlList.size();
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

    @Override
    public String toString() {
        return "DustLog{" +
                "total=" + total +
                ", userAssetDribbletlList=" + userAssetDribbletlList +
                '}';
    }

    /**
     *  The {@code AssetDribblets} class is useful to create a Binance's asset dribblets object
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

        /**
         * Constructor to init {@link AssetDribblets} object
         *
         * @param assetDribblets: asset dribblets details as {@link JSONObject}
         **/
        public AssetDribblets(JSONObject assetDribblets) {
            operateTime = assetDribblets.getLong("operateTime");
            totalTransferedAmount = assetDribblets.getDouble("totalTransferedAmount");
            totalServiceChargeAmount = assetDribblets.getDouble("totalServiceChargeAmount");
            transId = assetDribblets.getLong("transId");
            assetDribbletsDetailsList = getListDribbletsDetails(assetDribblets.getJSONArray("userAssetDribbletDetails"));
        }

        public long getOperateTime() {
            return operateTime;
        }

        public double getTotalTransferedAmount() {
            return totalTransferedAmount;
        }

        /**
         * Method to get {@link #totalTransferedAmount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #totalTransferedAmount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getTotalTransferedAmount(int decimals) {
            return roundValue(totalTransferedAmount, decimals);
        }

        public double getTotalServiceChargeAmount() {
            return totalServiceChargeAmount;
        }

        /**
         * Method to get {@link #totalServiceChargeAmount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #totalServiceChargeAmount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getTotalServiceChargeAmount(int decimals) {
            return roundValue(totalServiceChargeAmount, decimals);
        }

        public long getTransId() {
            return transId;
        }

        public ArrayList<DustItem> getAssetDribbletsDetailsList() {
            return assetDribbletsDetailsList;
        }

        public void insertAssetDribbletDetails(DustItem assetDribbletsDetails) {
            if (!assetDribbletsDetailsList.contains(assetDribbletsDetails))
                assetDribbletsDetailsList.add(assetDribbletsDetails);
        }

        public boolean removeAssetDribbletDetails(DustItem assetDribbletsDetails) {
            return assetDribbletsDetailsList.remove(assetDribbletsDetails);
        }

        public DustItem getAssetDribbletDetails(int index) {
            return assetDribbletsDetailsList.get(index);
        }

        @Override
        public String toString() {
            return "AssetDribblets{" +
                    "operateTime=" + operateTime +
                    ", totalTransferedAmount=" + totalTransferedAmount +
                    ", totalServiceChargeAmount=" + totalServiceChargeAmount +
                    ", transId=" + transId +
                    ", assetDribbletsDetailsList=" + assetDribbletsDetailsList +
                    '}';
        }

    }

}
