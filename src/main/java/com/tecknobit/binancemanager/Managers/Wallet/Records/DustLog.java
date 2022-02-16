package com.tecknobit.binancemanager.Managers.Wallet.Records;

import java.util.ArrayList;

public record DustLog(int total,ArrayList<AssetDribblets> userAssetDribblets) {

    @Override
    public int total() {
        return total;
    }

    @Override
    public ArrayList<AssetDribblets> userAssetDribblets() {
        return userAssetDribblets;
    }

    public record AssetDribblets(long operateTime, double totalTransferedAmount, double totalServiceChargeAmount,
                                 long transId,ArrayList<AssetDribbletsDetails> assetDribbletsDetails) {

        @Override
        public long operateTime() {
            return operateTime;
        }

        @Override
        public double totalTransferedAmount() {
            return totalTransferedAmount;
        }

        @Override
        public double totalServiceChargeAmount() {
            return totalServiceChargeAmount;
        }

        @Override
        public long transId() {
            return transId;
        }

        @Override
        public ArrayList<AssetDribbletsDetails> assetDribbletsDetails() {
            return assetDribbletsDetails;
        }

    }

    public record AssetDribbletsDetails (long transId, double serviceChargeAmount, double amount, long operateTime,
                                         double transferedAmount, String fromAsset) {

        @Override
        public long transId() {
            return transId;
        }

        @Override
        public double serviceChargeAmount() {
            return serviceChargeAmount;
        }

        @Override
        public double amount() {
            return amount;
        }

        @Override
        public long operateTime() {
            return operateTime;
        }

        @Override
        public double transferedAmount() {
            return transferedAmount;
        }

        @Override
        public String fromAsset() {
            return fromAsset;
        }

    }

}
