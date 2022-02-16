package com.tecknobit.binancemanager.Managers.Wallet.Records.Asset;

import java.util.ArrayList;

public record AssetDividend (int total, ArrayList<AssetDividendDetails> assetDividendDetails) {

    @Override
    public int total() {
        return total;
    }

    @Override
    public ArrayList<AssetDividendDetails> assetDividendDetails() {
        return assetDividendDetails;
    }

    public record AssetDividendDetails (long id, double amount, String asset, long divTime, String enInfo, long tranId) {

        @Override
        public long id() {
            return id;
        }

        @Override
        public double amount() {
            return amount;
        }

        @Override
        public String asset() {
            return asset;
        }

        @Override
        public long divTime() {
            return divTime;
        }

        @Override
        public String enInfo() {
            return enInfo;
        }

        @Override
        public long tranId() {
            return tranId;
        }

    }

}
