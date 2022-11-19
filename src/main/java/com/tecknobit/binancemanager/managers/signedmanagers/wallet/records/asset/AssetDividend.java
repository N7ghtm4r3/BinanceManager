package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.asset;

import com.tecknobit.apimanager.formatters.JsonHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code AssetDividend} class is useful to manage a Binance's asset dividend
 *
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data</a>
 **/

public class AssetDividend {

    /**
     * {@code total} is instance that memorizes total size of {@link #assetDividendDetailsList}
     **/
    private int total;

    /**
     * {@code assetDividendDetailsList} is instance that memorizes list of {@link AssetDividendDetails}
     **/
    private ArrayList<AssetDividendDetails> assetDividendDetailsList;

    /**
     * Constructor to init {@link AssetDividend} object
     *
     * @param total:                total size of {@link #assetDividendDetailsList}
     * @param assetDividendDetails: list of {@link AssetDividendDetails}
     **/
    public AssetDividend(int total, ArrayList<AssetDividendDetails> assetDividendDetails) {
        this.total = total;
        this.assetDividendDetailsList = assetDividendDetails;
    }

    /**
     * Constructor to init {@link AssetDividend} object
     *
     * @param assetDividend : asset dividend details as {@link JSONObject}
     * @implSpec if tradingAuthorityExpirationTime = -1 means that is not set for this api key
     **/
    public AssetDividend(JSONObject assetDividend) {
        total = assetDividend.getInt("total");
        assetDividendDetailsList = new ArrayList<>();
        JSONArray assetsDividend = JsonHelper.getJSONArray(assetDividend, "rows", new JSONArray());
        for (int j = 0; j < assetsDividend.length(); j++)
            assetDividendDetailsList.add(new AssetDividendDetails(assetsDividend.getJSONObject(j)));
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

    @Override
    public String toString() {
        return "AssetDividend{" +
                "total=" + total +
                ", assetDividendDetailsList=" + assetDividendDetailsList +
                '}';
    }

    /**
     *  The {@code AssetDividendDetails} class is useful to create an asset with its dividend details
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

        /**
         * Constructor to init {@link AssetDividendDetails} object
         *
         * @param dividendDetails: dividend details as {@link JSONObject}
         * @implSpec if tradingAuthorityExpirationTime = -1 means that is not set for this api key
         **/
        public AssetDividendDetails(JSONObject dividendDetails) {
            id = dividendDetails.getLong("id");
            amount = dividendDetails.getDouble("amount");
            asset = dividendDetails.getString("asset");
            divTime = dividendDetails.getLong("divTime");
            enInfo = dividendDetails.getString("enInfo");
            tranId = dividendDetails.getLong("tranId");
        }

        public long getId() {
            return id;
        }

        public double getAmount() {
            return amount;
        }

        /**
         * Method to get {@link #amount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #amount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getAmount(int decimals) {
            return roundValue(amount, decimals);
        }

        public String getAsset() {
            return asset;
        }

        public long getDivTime() {
            return divTime;
        }

        public String getEnInfo() {
            return enInfo;
        }

        public long getTranId() {
            return tranId;
        }

        @Override
        public String toString() {
            return "AssetDividendDetails{" +
                    "id=" + id +
                    ", amount=" + amount +
                    ", asset='" + asset + '\'' +
                    ", divTime=" + divTime +
                    ", enInfo='" + enInfo + '\'' +
                    ", tranId=" + tranId +
                    '}';
        }

    }

}
