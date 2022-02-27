package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Asset;

import java.util.ArrayList;

/**
 * The {@code AssetDividend} class is useful to manage AssetDividend Binance request
 * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data
 * **/

public class AssetDividend {

    private final int total;
    private final ArrayList<AssetDividendDetails> assetDividendDetails;

    public AssetDividend(int total, ArrayList<AssetDividendDetails> assetDividendDetails) {
        this.total = total;
        this.assetDividendDetails = assetDividendDetails;
    }

    public int total() {
        return total;
    }

    public ArrayList<AssetDividendDetails> assetDividendDetails() {
        return assetDividendDetails;
    }

    /**
     *  The {@code AssetDividendDetails} class is useful to obtain and format AssetDividendDetails object
     *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data
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
